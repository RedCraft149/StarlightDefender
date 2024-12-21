package com.redcraft.starlight.shared.events;

import com.redcraft.starlight.shared.entity.Entity;

public class HealthChangeEvent extends EntityEvent{

    float newHealth;
    float oldHealth;

    public HealthChangeEvent(Entity entity, float newHealth, float oldHealth) {
        super(entity);
        this.newHealth = newHealth;
        this.oldHealth = oldHealth;
    }

    public float getNewHealth() {
        return newHealth;
    }
    public float getOldHealth() {
        return oldHealth;
    }
}
