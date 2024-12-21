package com.redcraft.starlight.shared.events;

import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.rlib.events.Event;

public abstract class EntityEvent implements Event {
    protected final Entity entity;

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
