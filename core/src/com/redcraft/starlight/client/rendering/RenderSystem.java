package com.redcraft.starlight.client.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.redcraft.starlight.client.rendering.particles.ParticleSystem;

public class RenderSystem implements Disposable {

    public static final int ALPHA = 1;
    public static final int SPRITES = 2;
    public static final int LINES = 4;
    public static final int SOLIDS = 8;
    public static final int OVERLAY = 16;
    public static final int WORLD_PROJECTION = 32;
    public static final int WORLD_TRANSFORM = 64;

    SpriteManager  sprites;
    ShapeRenderer  lines;
    ShapeRenderer  solids;
    SpriteBatch    overlays;
    ParticleSystem particles;

    Matrix4 worldProjection;
    Matrix3 worldTransform;

    Matrix4 screenProjection;

    public RenderSystem(Matrix4 projection) {
        create(projection);
    }

    private void create(Matrix4 projection) {
        sprites = new SpriteManager();
        lines = new ShapeRenderer();
        solids = new ShapeRenderer();
        overlays = new SpriteBatch();
        this.worldProjection = new Matrix4(projection);
        worldTransform = new Matrix3().idt();
        screenProjection = new Matrix4().setToOrtho2D(0f,0f,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        particles = new ParticleSystem();
    }

    public void clear() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f,0f,0f,1f);
    }

    public void begin(int code) {
        if((code & ALPHA)!=0) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        if((code & SPRITES)!=0) {
            sprites.currentShader = null; //Causes the right shader to be bound when SpriteManager#renderAll is called.
        }
        if((code & LINES)!=0) {
            if((code & WORLD_PROJECTION)!=0) lines.setProjectionMatrix(worldProjection);
            else lines.setProjectionMatrix(screenProjection);
            if((code & WORLD_TRANSFORM)!=0) lines.setTransformMatrix(new Matrix4().set(worldTransform));
            else lines.setTransformMatrix(new Matrix4());
            lines.begin(ShapeRenderer.ShapeType.Line);
        }
        if((code & SOLIDS)!=0) {
            if((code & WORLD_PROJECTION)!=0) solids.setProjectionMatrix(worldProjection);
            else solids.setProjectionMatrix(screenProjection);
            if((code & WORLD_TRANSFORM)!=0) solids.setTransformMatrix(new Matrix4().set(worldTransform));
            else solids.setTransformMatrix(new Matrix4());
            solids.begin(ShapeRenderer.ShapeType.Filled);
        }
        if((code & OVERLAY)!=0) {
            if((code & WORLD_PROJECTION)!=0) overlays.setProjectionMatrix(worldProjection);
            if((code & WORLD_TRANSFORM)!=0) overlays.setTransformMatrix(new Matrix4().set(worldTransform));
            overlays.begin();
        }
    }
    public void end(int code) {
        if((code & SPRITES)!=0) {} //...;
        if((code & LINES)!=0) lines.end();
        if((code & SOLIDS)!=0) solids.end();
        if((code & OVERLAY)!=0) overlays.end();
        if((code & ALPHA)!=0) Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void bindSpriteShader(ShaderProgram shader) {
        shader.bind();
        shader.setUniformMatrix("u_projection", worldProjection);
        shader.setUniformMatrix("u_worldTransform",worldTransform);
    }
    public void setWorldTransform(Matrix3 worldTransform) {
        this.worldTransform.set(worldTransform);
    }
    public void setWorldProjection(Matrix4 worldProjection) {
        this.worldProjection.set(worldProjection);
    }

    public SpriteManager sprites() {
        return sprites;
    }
    public ShapeRenderer lines() {
        return lines;
    }
    public ShapeRenderer solids() {
        return solids;
    }
    public SpriteBatch overlays() {
        return overlays;
    }
    public ParticleSystem particles() {
        return particles;
    }

    public void renderAllSprites() {
        sprites.renderAll(this);
    }

    @Override
    public void dispose() {
        sprites.dispose();
        lines.dispose();
        solids.dispose();
        overlays.dispose();

        worldProjection = null;
        worldTransform = null;
    }
}
