package com.redcraft.starlight.client.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.world.Chunk;

public class CChunk extends Chunk {

    public static boolean DEBUG = false;

    public CChunk(int x, int y) {
        super(x, y);
        createComponents();
    }

    public void draw(RenderSystem renderSystem) {
        boolean containsPlayer = false;
        for(Entity entity : getEntities()) {
            entity.as(CEntity.class).draw(renderSystem);
            if(entity instanceof CPlayer) containsPlayer = true;
        }
        if(containsPlayer) renderSystem.lines().setColor(Color.GREEN);
        if(DEBUG) renderSystem.lines().rect(x,y,1,1);
        if(containsPlayer) renderSystem.lines().setColor(Color.WHITE);
    }

    public void setRelation(float x, float y) {
        for(Entity entity : getEntities()) {
            entity.as(CEntity.class).setRelation(x,y);
            entity.as(CEntity.class).recalculateTransformMatrix();
        }
    }
    public void step() {
        Vector2 relation = Shared.CLIENT.get(CComponents.universeRelation ,Vector2.class);
        setRelation(relation.x,relation.y);
        entities().removeIf(entity -> !entity.exists());
    }
}
