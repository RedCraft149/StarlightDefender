package com.redcraft.starlight.shared.entity;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.util.RMath;

import java.util.UUID;

/**
 * Represents an Entity that lives in a modular space (torus topology).
 */
public class ModularEntity extends Entity {

    protected final Vector2 dimensions;

    public ModularEntity(UUID uuid, Vector2 dimensions, boolean initWithComponents) {
        super(uuid, initWithComponents);
        this.dimensions = new Vector2(dimensions);
    }

    @Override
    public void setPosition(float x, float y) {
        Vector2 tmp = new Vector2(x,y);
        RMath.mod(tmp,dimensions);
        super.setPosition(tmp.x,tmp.y);
    }

    public void setDimensions(float w, float h) {
        this.dimensions.set(w,h);
    }

    public Vector2 getModularPosition(Vector2 relation) {
        Vector2 mod = new Vector2(position);
        RMath.nearestModularVector(mod,relation,dimensions);
        return mod;
    }
}
