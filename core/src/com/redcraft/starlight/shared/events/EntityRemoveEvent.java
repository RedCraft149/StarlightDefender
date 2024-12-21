package com.redcraft.starlight.shared.events;

import com.redcraft.starlight.shared.entity.Entity;

public class EntityRemoveEvent extends EntityEvent{
    public EntityRemoveEvent(Entity entity) {
        super(entity);
    }
}
