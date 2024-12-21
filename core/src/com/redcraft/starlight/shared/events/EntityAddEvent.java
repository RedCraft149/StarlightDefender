package com.redcraft.starlight.shared.events;

import com.redcraft.starlight.shared.entity.Entity;

public class EntityAddEvent extends EntityEvent{
    public EntityAddEvent(Entity entity) {
        super(entity);
    }
}
