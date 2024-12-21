package com.redcraft.starlight.client.elements;


import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.world.Chunk;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.function.Consumer;
import com.redcraft.starlight.util.RMath;

import java.util.UUID;

public class CUniverse extends Universe {

    float projWidth, projHeight;
    CPlayer player;
    StarBackground background;

    public CUniverse(int width, int height, UUID playerUUID) {
        super(width, height, Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class));
        Shared.CLIENT.set(CComponents.universeDimensions,new Vector2(width,height));

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                chunks[x*height+y] = new CChunk(x,y);
            }
        }

        Vector2 projDimensions = Shared.CLIENT.get(CComponents.projectionDimensions,Vector2.class);
        projWidth = projDimensions.x;
        projHeight = projDimensions.y;

        player = new CPlayer(playerUUID);
        entityDistributor.addEntity(player);

        Shared.CLIENT.run(CComponents.createControls, player);

        background = new StarBackground(0.0001f);
    }

    public void forAllVisibleChunks(Consumer<Chunk> action) {
        int hdcw = RMath.ceil(projWidth/2) + 1;
        int hdch = RMath.ceil(projHeight/2)+ 1;
        int px = chunkX(player.getPosition().x);
        int py = chunkY(player.getPosition().y);
        for(int x = px-hdcw; x <= px+hdcw; x++) {
            for(int y = py-hdch; y <= py+hdch; y++) {
                int rx = RMath.mod(x,width);
                int ry = RMath.mod(y,height);
                action.accept(getChunk(rx,ry));
            }
        }
    }

    public void draw(RenderSystem system) {
        forAllVisibleChunks(chunk -> chunk.as(CChunk.class).draw(system));
    }

    public void render(RenderSystem system) {
        system.setWorldTransform(new Matrix3().idt());
        background.render(system);
        float worldScale = Shared.CLIENT.getFloat(CComponents.worldScale)+1f;
        system.setWorldTransform(player.createWorldTransform(projWidth*worldScale,projHeight*worldScale));
        system.setWorldProjection(new Matrix4().setToOrtho2D(0,0,projWidth*worldScale,projHeight*worldScale));
        system.begin(RenderSystem.ALPHA | RenderSystem.SPRITES | RenderSystem.LINES | RenderSystem.WORLD_TRANSFORM | RenderSystem.WORLD_PROJECTION);
        draw(system);
        system.particles().draw(system);
        system.particles().process(1/60f);
        system.sprites().flushAll();
        system.renderAllSprites();
        system.end(RenderSystem.ALPHA | RenderSystem.SPRITES | RenderSystem.LINES);
    }

    public void step(float dt) {
        Shared.CLIENT.set(CComponents.universeRelation, player.getPosition());
        forAllVisibleChunks(chunk -> chunk.as(CChunk.class).step());
    }
    public void distributeEntities() {
        entityDistributor.distribute();
    }

    public CPlayer getPlayer() {
        return player;
    }
}
