package com.redcraft.starlight.shared.events;

import com.redcraft.starlight.shared.entity.Entity;

public class PlayerPauseTimerChangeEvent extends EntityEvent{

    float timer;

    public PlayerPauseTimerChangeEvent(Entity entity, float timer) {
        super(entity);
        this.timer = timer;
    }

    public float timer() {
        return timer;
    }
}
