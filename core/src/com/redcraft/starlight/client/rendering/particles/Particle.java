package com.redcraft.starlight.client.rendering.particles;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.rlib.components.ComponentSystemAccessor;
import com.redcraft.starlight.client.rendering.Drawable;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.rlib.Processable;
import com.redcraft.rlib.function.BiConsumer;

public class Particle extends ComponentSystemAccessor implements Processable, Drawable {
    float lifetime;
    private final Vector2 position;
    private final Vector2 velocity;
    private float rotation;
    private float scale;
    private final Matrix3 transform;
    private BiConsumer<Particle,Float> tickFunction;
    private final String texture;
    private float alpha;

    public Particle(float lifetime, Vector2 position, Vector2 velocity, BiConsumer<Particle,Float> tickFunction, float scale, String texture) {
        super();
        this.lifetime = lifetime;
        this.position = new Vector2(position);
        this.velocity = new Vector2(velocity);
        this.tickFunction = tickFunction;
        this.scale = scale;
        this.rotation = 0f;
        this.texture = texture;
        this.alpha = 1f;

        transform = new Matrix3();
        recalculateTransformMatrix();
    }

    @Override
    public void process(float dt) {
        lifetime -= dt;
        position.add(velocity);
        if(tickFunction != null) tickFunction.accept(this,dt);
        recalculateTransformMatrix();
    }

    private void recalculateTransformMatrix() {
        transform.idt().trn(position).scl(scale).rotateRad(rotation);
    }

    @Override
    public void draw(RenderSystem system) {
        system.sprites().draw(texture,transform,alpha);
    }

    public void setLifetime(float lifetime) {
        this.lifetime = lifetime;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setTickFunction(BiConsumer<Particle, Float> tickFunction) {
        this.tickFunction = tickFunction;
    }
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }
    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float lifetime() {
        return lifetime;
    }

    public Vector2 position() {
        return new Vector2(position);
    }

    public Vector2 velocity() {
        return new Vector2(velocity);
    }

    public float rotation() {
        return rotation;
    }

    public float scale() {
        return scale;
    }

    public String texture() {
        return texture;
    }

    public float alpha() {
        return alpha;
    }

}
