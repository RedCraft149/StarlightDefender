package com.redcraft.starlight.server.elements;

import com.redcraft.starlight.server.collision.CollisionAlgorithmProvider;
import com.redcraft.starlight.server.collision.Shape;

import java.util.UUID;

public class SCollisionEntity extends SEntity{

    Shape hitbox;

    public SCollisionEntity(UUID uuid, Shape hitbox) {
        super(uuid);
        this.hitbox = hitbox;
    }

    public Shape getHitbox() {
        return hitbox.setPosition(position.x,position.y);
    }

    public boolean collide(SCollisionEntity other, CollisionAlgorithmProvider provider) {
        return provider.getAlgorithm(this.hitbox,other.hitbox).collideUnknown(this.hitbox,other.hitbox);
    }
}
