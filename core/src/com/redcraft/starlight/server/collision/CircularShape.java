package com.redcraft.starlight.server.collision;

import com.badlogic.gdx.math.Vector2;

public class CircularShape implements Shape {
    public float x, y, radius;

    public CircularShape() {
    }

    public CircularShape(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public Shape setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public float getBoundingRadius() {
        return radius;
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(x,y);
    }
}
