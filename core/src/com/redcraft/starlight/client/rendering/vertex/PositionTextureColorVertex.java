package com.redcraft.starlight.client.rendering.vertex;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class PositionTextureColorVertex implements Vertex {

    Vector2 position;
    Vector2 uv;
    Color color;

    public PositionTextureColorVertex(Vector2 position, Vector2 uv, Color color) {
        this.position = position;
        this.uv = uv;
        this.color = color;
    }

    public PositionTextureColorVertex(float x, float y, float u, float v, float r, float g, float b, float a) {
        this(new Vector2(x,y),new Vector2(u,v),new Color(r,g,b,a));
    }

    @Override
    public void transform(Matrix3 transform) {
        position.mul(transform);
    }

    @Override
    public int stride() {
        return 8;
    }

    @Override
    public float[] toFloatArray() {
        return new float[]{position.x,position.y,uv.x,uv.y,color.r,color.g,color.b,color.a};
    }

    public static Vertex[] postionTextureColorSprite() {
        return new Vertex[]{
                new PositionTextureColorVertex(-0.5f,-0.5f,0,0,1,1,1,1),
                new PositionTextureColorVertex(-0.5f, 0.5f,0,1,1,1,1,1),
                new PositionTextureColorVertex( 0.5f, 0.5f,1,1,1,1,1,1),
                new PositionTextureColorVertex( 0.5f,-0.5f,1,0,1,1,1,1)
        };
    }
    public static Vertex[] postionTextureColorSprite(float alpha) {
        return new Vertex[]{
                new PositionTextureColorVertex(-0.5f,-0.5f,0,0,1,1,1,alpha),
                new PositionTextureColorVertex(-0.5f, 0.5f,0,1,1,1,1,alpha),
                new PositionTextureColorVertex( 0.5f, 0.5f,1,1,1,1,1,alpha),
                new PositionTextureColorVertex( 0.5f,-0.5f,1,0,1,1,1,alpha)
        };
    }
    public static Vertex[] postionTextureColorSprite(float r, float g, float b, float a) {
        return new Vertex[]{
                new PositionTextureColorVertex(-0.5f,-0.5f,0,0,r,g,b,a),
                new PositionTextureColorVertex(-0.5f, 0.5f,0,1,r,g,b,a),
                new PositionTextureColorVertex( 0.5f, 0.5f,1,1,r,g,b,a),
                new PositionTextureColorVertex( 0.5f,-0.5f,1,0,r,g,b,a)
        };
    }
}
