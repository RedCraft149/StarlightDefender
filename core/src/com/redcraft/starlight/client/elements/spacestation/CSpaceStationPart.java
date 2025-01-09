package com.redcraft.starlight.client.elements.spacestation;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CEntity;
import com.redcraft.starlight.client.events.ElementDestroyEvent;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.client.rendering.vertex.PositionTextureColorVertex;
import com.redcraft.starlight.client.rendering.vertex.Vertex;
import com.redcraft.starlight.shared.Shared;

import java.util.UUID;

public abstract class CSpaceStationPart extends CEntity {
    protected int direction;
    protected Vector2[] uvs;
    protected float uvSize;

    public CSpaceStationPart(UUID uuid, Vector2[] uvs, float uvSize) {
        super(uuid);
        this.uvs = uvs;
        this.uvSize = uvSize;
    }

    @Override
    public void draw(RenderSystem system) {
        system.sprites().draw(texture,transform,getVertices(direction,1f,1f,1f,1f));
    }

    private Vertex[] getVertices(int direction, float r, float g, float b, float a) {
        Vector2 uv = new Vector2(uvs[direction]).scl(uvSize);
        return new Vertex[]{
                new PositionTextureColorVertex(-0.5f,-0.5f,uv.x,uv.y,r,g,b,a),
                new PositionTextureColorVertex(-0.5f, 0.5f,uv.x,uv.y+uvSize,r,g,b,a),
                new PositionTextureColorVertex( 0.5f, 0.5f,uv.x+uvSize,uv.y+uvSize,r,g,b,a),
                new PositionTextureColorVertex( 0.5f,-0.5f,uv.x+uvSize,uv.y,r,g,b,a)
        };
    }

    @Override
    public void remove() {
        super.remove();
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).throwEvent(new ElementDestroyEvent(this,8,true));
    }

    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
}
