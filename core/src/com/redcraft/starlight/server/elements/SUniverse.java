package com.redcraft.starlight.server.elements;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.communication.server.GServer;
import com.redcraft.starlight.server.SConstants;
import com.redcraft.starlight.server.collision.Collider;
import com.redcraft.starlight.server.collision.CollisionAlgorithmProvider;
import com.redcraft.starlight.server.world.ChunkPopulator;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entities;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.ChunkCreationPacket;
import com.redcraft.starlight.shared.packets.PackedEntity;
import com.redcraft.starlight.shared.world.Chunk;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.rlib.Pair;
import com.redcraft.rlib.Processable;
import com.redcraft.rlib.events.EventHandler;

import java.util.*;

public class SUniverse extends Universe implements Processable {

    Collider collider;
    GServer<?> server;
    ChunkPopulator populator;

    public SUniverse(int width, int height, GServer<?> server) {
        super(width, height, Shared.SERVER.get(SConstants.gameEventHandler, EventHandler.class));
        populator = new ChunkPopulator();
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                chunks[x*height+y] = new SChunk(x,y);
                populator.populate(this,2,x*height+y);
            }
        }

        collider = new Collider(this, CollisionAlgorithmProvider.defaultConfiguration(new Vector2(width,height)), this::onEntitiesCollide);
        eventHandler.register(collider);
        this.server = server;
    }

    public ChunkCreationPacket createPacketForChunk(int i) {
        Chunk chunk = getChunk(i);
        Set<Map.Entry<UUID, Entity>> entities = chunk.entities().entrySet();
        PackedEntity[] packedEntities = new PackedEntity[entities.size()];

        int index = 0;
        for(Map.Entry<UUID,Entity> entry : entities) {
            Entity e = entry.getValue();
            packedEntities[index] = new PackedEntity(entry.getKey(), Entities.typeServer(e), e.getPosition().x, e.getPosition().y);
            index++;
        }

        System.out.println(new ChunkCreationPacket(i,packedEntities));
        return new ChunkCreationPacket(i,packedEntities);
    }

    public void process(float dt) {
        collider.process(dt);
        for(Chunk chunk : chunks) {
            chunk.as(SChunk.class).process(dt);
        }
        entityDistributor.distribute();
    }

    public void onEntitiesCollide(Pair<SCollisionEntity, SCollisionEntity> pair) {
        if(pair.getA() == pair.getB()) {
            System.out.println("collide self");
        }
        collide(pair.getA(),pair.getB());
        collide(pair.getB(),pair.getA());
    }

    public void collide(Entity e, Entity other) {

        if(e instanceof SPlayer) {
            SPlayer player = (SPlayer) e;
            player.damage(evaluateDamage(other),other.getPosition().x,other.getPosition().y);
        } else if(e instanceof SBullet) {
            SBullet bullet = (SBullet) e;
            if(other instanceof SBullet) return;
            if(bullet.owner instanceof SPlayer) {
                SPlayer owner = (SPlayer) bullet.owner;
                owner.addScore(evaluateKillScore(other));
                owner.addAmmo(evaluateDroppedAmmo(other));
            }
            bullet.remove();
        } else {
            e.remove();
        }
    }

    private int evaluateKillScore(Entity e) {
        if(e instanceof SStaticObject) return ((SStaticObject) e).getScore();
        if(e instanceof SEnemy) return 7;
        return 1;
    }
    private float evaluateDamage(Entity e) {
        if(e instanceof SStaticObject) return ((SStaticObject) e).getDamage();
        if(e instanceof SEnemy) return -0.35f;
        return -0.05f;
    }
    private int evaluateDroppedAmmo(Entity e) {
        if(e instanceof SEnemy) {
            SEnemy enemy = (SEnemy) e;
            if(enemy.getType() == SEnemy.BASIC) return 5;
            if(enemy.getType() == SEnemy.ADVANCED) return 15;
        }
        if(e instanceof SPlayer) {
            SPlayer player = (SPlayer) e;
            return player.getAmmo() / 2;
        }
        return 0;
    }

    public void addEnemyFor(SEntity e) {
        float angle = MathUtils.random(0,MathUtils.PI2);
        Vector2 center = e.getPosition();
        center.add(new Vector2(1,0).setAngleRad(angle).scl(1.2f));
        SEnemy enemy = new SEnemy(UUID.randomUUID(),e,this,MathUtils.randomBoolean(0.2f) ? SEnemy.ADVANCED : SEnemy.BASIC);
        enemy.setPosition(center.x,center.y);
        entityDistributor.addEntity(enemy);
    }
    public SEnemy addEnemy(SEntity target, Vector2 position, int type) {
        SEnemy enemy = new SEnemy(UUID.randomUUID(),target,this,type);
        enemy.setPosition(position.x, position.y);
        entityDistributor.addEntity(enemy);
        return enemy;
    }
}
