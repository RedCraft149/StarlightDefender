package com.redcraft.starlight.server.elements;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.server.collision.CircularShape;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.rlib.Processable;

import java.util.UUID;

public class SBullet extends SCollisionEntity implements Processable {

    Vector2 velocity;
    Entity owner;
    float lifetime;

    public SBullet(UUID uuid, Vector2 velocity, Vector2 position, Entity owner, float lifetime) {
        super(uuid, new CircularShape(position.x,position.y,/*0.025f*/0.05f));
        this.velocity = new Vector2(velocity);
        setPosition(position.x,position.y);
        setRotation(velocity.angleRad() - MathUtils.HALF_PI);
        this.owner = owner;
        this.lifetime = lifetime;
    }

    @Override
    public void process(float dt) {
        move(velocity.x,velocity.y);
        lifetime -= dt;
        if(lifetime < 0f) remove();
    }
}
