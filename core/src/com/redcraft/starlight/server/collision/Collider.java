package com.redcraft.starlight.server.collision;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.server.elements.SCollisionEntity;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.events.EntityPositionChangedEvent;
import com.redcraft.starlight.shared.world.Chunk;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.rlib.Pair;
import com.redcraft.rlib.Processable;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.Listener;
import com.redcraft.rlib.function.Consumer;
import com.redcraft.starlight.util.RMath;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A thread save entity collider.
 */
public class Collider implements Processable, Listener {

    private final float additionalSearchDistance = 0.125f;

    private final List<Entity> moved;
    Universe universe;
    CollisionAlgorithmProvider provider;
    Consumer<Pair<SCollisionEntity,SCollisionEntity>> action;

    public Collider(Universe universe, CollisionAlgorithmProvider provider, Consumer<Pair<SCollisionEntity, SCollisionEntity>> action) {
        this.universe = universe;
        this.provider = provider;
        this.action = action;

        moved = new LinkedList<>();
    }

    public void setAction(Consumer<Pair<SCollisionEntity,SCollisionEntity>> onEntityCollide) {
        if(onEntityCollide==null) this.action = pair -> {};
        else this.action = onEntityCollide;
    }

    @EventListener
    public void onEntityMove(EntityPositionChangedEvent event) {
        synchronized (moved) {
            if(!moved.contains(event.getEntity())) moved.add(event.getEntity());
        }
    }

    @Override
    public void process(float dt) {
        LinkedList<Entity> copy;
        synchronized (moved) {
            if(moved.isEmpty()) return;
            copy = new LinkedList<>(moved);
            moved.clear();
        }

        //System.out.println("processing: "+copy.size()+" collisions");

        Set<Pair<SCollisionEntity,SCollisionEntity>> collisions = new HashSet<>();
        for(Entity e : copy) {
            if(!e.exists()) continue;
            SCollisionEntity hit = process(e);
            if(hit==null) continue;
            collisions.add(new Pair<>((SCollisionEntity) e,hit));
        }

        for(Pair<SCollisionEntity,SCollisionEntity> collision : collisions) {
            action.accept(collision);
        }
    }

    //TODO: one entity CAN collide with multiple entities, which is not respected by this implementation
    private SCollisionEntity process(Entity entity) {
        if(!(entity instanceof SCollisionEntity)) return null;
        SCollisionEntity collideable = (SCollisionEntity) entity;
        Shape hitbox = collideable.getHitbox();
        Vector2 postion = collideable.getPosition();

        int eCX = RMath.mod(RMath.floor(postion.x),universe.width());
        int eCY = RMath.mod(RMath.floor(postion.y),universe.height());

        SCollisionEntity collided = collisionInChunk(universe.getChunk(eCX,eCY),collideable);
        if(collided!=null) return collided;

        float minX = postion.x-hitbox.getBoundingRadius()-additionalSearchDistance;
        float minY = postion.y-hitbox.getBoundingRadius()-additionalSearchDistance;
        float maxX = postion.x+hitbox.getBoundingRadius()+additionalSearchDistance;
        float maxY = postion.y+hitbox.getBoundingRadius()+additionalSearchDistance;

        int minCX = RMath.floor(minX);
        int minCY = RMath.floor(minY);
        int maxCX = RMath.ceil(maxX);
        int maxCY = RMath.ceil(maxY);

        for(int x = minCX; x <= maxCX; x++) {
            for(int y = minCY; y <= maxCY; y++) {
                int rx = RMath.mod(x,universe.width());
                int ry = RMath.mod(y,universe.height());
                if(rx == eCX && ry == eCY) continue;
                Chunk chunk = universe.getChunk(rx,ry);
                SCollisionEntity e = collisionInChunk(chunk,collideable);
                if(e!=null) return e;
            }
        }

        return null;
    }

    private SCollisionEntity collisionInChunk(Chunk chunk, SCollisionEntity entity) {
        for(Entity e : chunk.getEntities()) {
            if(e==entity) continue;
            if(!e.exists()) continue;
            if(!(e instanceof SCollisionEntity)) continue;
            if(collide((SCollisionEntity) e, entity)) return (SCollisionEntity) e;
        }
        return null;
    }

    private boolean collide(SCollisionEntity a, SCollisionEntity b) {
        Shape shapeA = a.getHitbox();
        Shape shapeB = b.getHitbox();
        CollisionAlgorithm<?,?> algorithm = provider.getAlgorithm(shapeA,shapeB);
        if(algorithm==null) {
            System.err.println("No algorithm found for <"+shapeA.getClass()+", "+shapeB.getClass()+">");
            return false;
        }

        return algorithm.collideUnknown(shapeA,shapeB);
    }
}
