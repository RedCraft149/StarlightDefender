package com.redcraft.starlight.server.collision;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.util.RMath;

public class CCAlgorithm implements CollisionAlgorithm<CircularShape,CircularShape>{

    private final Vector2 dimensions;

    public CCAlgorithm(Vector2 dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public boolean collide(CircularShape a, CircularShape b) {
        //TODO modular correction on centers
        float dx = RMath.modularDistance(a.x,b.x,dimensions.x);
        float dy = RMath.modularDistance(a.y,b.y,dimensions.y);
        float distance = dx * dx + dy * dy;
        float radiusSum = a.radius + b.radius;
        return distance < radiusSum * radiusSum;
    }

    @Override
    public Class<CircularShape> getTypeA() {
        return CircularShape.class;
    }

    @Override
    public Class<CircularShape> getTypeB() {
        return CircularShape.class;
    }
}
