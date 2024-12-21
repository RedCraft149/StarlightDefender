package com.redcraft.starlight.client.rendering.vertex;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class PositionTextureVertex implements Vertex {

    Vector2 position;
    Vector2 uv;

    public PositionTextureVertex(Vector2 position, Vector2 uv) {
        this.position = position;
        this.uv = uv;
    }
    public PositionTextureVertex(float x, float y, float u, float v) {
        this(new Vector2(x,y),new Vector2(u,v));
    }

    @Override
    public void transform(Matrix3 transform) {
        position.mul(transform);
    }

    @Override
    public int stride() {
        return 4;
    }

    @Override
    public float[] toFloatArray() {
        return new float[]{position.x,position.y,uv.x,uv.y};
    }

    public static Vertex[] postionTextureSprite() {
        return new Vertex[]{
                new PositionTextureVertex(-0.5f,-0.5f,0,0),
                new PositionTextureVertex(-0.5f, 0.5f,0,1),
                new PositionTextureVertex( 0.5f, 0.5f,1,1),
                new PositionTextureVertex( 0.5f,-0.5f,1,0)
        };
    }
}
