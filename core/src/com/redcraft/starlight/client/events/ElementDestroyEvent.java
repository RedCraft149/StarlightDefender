package com.redcraft.starlight.client.events;

import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.events.EntityEvent;

public class ElementDestroyEvent extends EntityEvent {
    public int particles;
    public boolean playSound;
    public ElementDestroyEvent(Entity entity, int particles, boolean playSound) {
        super(entity);
        this.particles = particles;
        this.playSound = playSound;
    }
}
