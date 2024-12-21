package com.redcraft.starlight.shared.events;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.shared.entity.Entity;

public class EntityPositionChangedEvent extends EntityEvent {
    protected final Vector2 from, to;

    public EntityPositionChangedEvent(Vector2 from, Vector2 to, Entity entity) {
        super(entity);
        this.from = from;
        this.to = to;
    }

    public Vector2 from() {
        return new Vector2(from);
    }

    public Vector2 to() {
        return new Vector2(to);
    }
}
