package com.redcraft.starlight.shared.events;

import com.redcraft.starlight.shared.entity.Entity;

public class PlayerDeathTimerChangeEvent extends EntityEvent{

    float deathTimer;

    public PlayerDeathTimerChangeEvent(Entity entity, float deathTimer) {
        super(entity);
        this.deathTimer = deathTimer;
    }

    public float deathTimer() {
        return deathTimer;
    }
}
