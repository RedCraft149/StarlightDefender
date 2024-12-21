package com.redcraft.starlight.shared.events;

import com.redcraft.starlight.shared.entity.Entity;

public class EntityRotationChangedEvent extends EntityEvent{

    protected final float toRad;

    public EntityRotationChangedEvent(Entity entity, float toRad) {
        super(entity);
        this.toRad = toRad;
    }

    public float toRad() {
        return toRad;
    }
}
