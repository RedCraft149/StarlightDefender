package com.redcraft.starlight.client.events;

import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.events.EntityEvent;

public class PlayerShootEvent extends EntityEvent {
    public PlayerShootEvent(Entity entity) {
        super(entity);
    }
}
