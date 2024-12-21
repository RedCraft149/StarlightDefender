package com.redcraft.starlight.shared.events;

import com.redcraft.starlight.shared.entity.Entity;

public class PlayerDeathEvent extends EntityEvent{
    public PlayerDeathEvent(Entity entity) {
        super(entity);
    }
}
