package com.redcraft.starlight.server.elements;

import com.redcraft.starlight.server.collision.CircularShape;
import com.redcraft.starlight.shared.entity.StaticObjects;

import java.util.UUID;

public class SStaticObject extends SCollisionEntity {
    int type;

    public SStaticObject(UUID uuid, int type) {
        super(uuid, new CircularShape(0f,0f,1/32f));
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getScore() {
        if(type == StaticObjects.ROCK) return 1;
        if(type == StaticObjects.MINE) return 3;
        return 0;
    }

    public float getDamage() {
        if(type == StaticObjects.ROCK) return -0.05f;
        if(type == StaticObjects.MINE) return -0.15f;
        return 0.01f;
    }
}
