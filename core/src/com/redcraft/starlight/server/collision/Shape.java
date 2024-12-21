package com.redcraft.starlight.server.collision;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface Shape {
    Shape setPosition(float x, float y);
    float getBoundingRadius();
    Vector2 getPosition();
}
