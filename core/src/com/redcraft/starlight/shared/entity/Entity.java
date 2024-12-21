package com.redcraft.starlight.shared.entity;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.shared.events.EntityEvents;
import com.redcraft.rlib.components.ComponentSystem;
import com.redcraft.rlib.components.ComponentSystemAccessor;
import com.redcraft.rlib.Castable;
import com.redcraft.rlib.keyless.KeyProvider;

import java.util.UUID;

/**
 * <p>Base class for all entities in the client and server systems.
 * All entities have a: </p>
 *  <ol>
 *      <li>position</li>
 *      <li>rotation</li>
 *      <li>{@link UUID}</li>
 *      <li>existence marker ({@link Entity#exists})</li>
 *      <li>{@link ComponentSystem}, which may be null</li>
 *  </ol>
 *  The UUID of an entity cannot be changed in any way after creation.
 *
 */
public class Entity extends ComponentSystemAccessor implements KeyProvider<UUID>, Castable<Entity> {
    protected final Vector2 position;
    protected float rotation;
    /**
     * Existence marker of this entity.
     * If this is set to false (usually via {@link Entity#remove()}),
     * this entity should be no longer be used and removed from any chunks.
     */
    protected boolean exists;
    protected final UUID uuid;

    public Entity(UUID uuid, boolean initWithComponents) {
        position = new Vector2();
        rotation = 0f;
        exists = true;
        this.uuid = uuid;

        if(initWithComponents) createComponents();
    }

    public void setPosition(float x, float y) {
        EntityEvents.positionChanged(this,x,y,null);
        position.set(x,y);
    }
    public void move(float dx, float dy) {
        if(!exists) return;
        setPosition(position.x+dx,position.y+dy);
    }
    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void setRotation(float rad) {
        EntityEvents.rotationChanged(this,rad,null);
        rotation = rad;
    }
    public float getRotation() {
        return rotation;
    }

    public void remove() {
        EntityEvents.removed(this,null);
        exists = false;
    }
    public void removeSilent() {
        exists = false;
    }
    public boolean exists() {
        return exists;
    }

    public UUID getUUID() {
        return uuid;
    }

    //Equivalent to getUUID(), but allows Entity to be used with KeylessMap.
    @Override
    public UUID getKey() {
        return uuid;
    }

    public String toString() {
        return getClass().getSimpleName()+"["+uuid+","+exists+"]";
    }

    public int hashCode() {
        return uuid.hashCode();
    }
    public boolean equals(Object other) {
        if(!(other instanceof Entity)) return false;
        Entity e = (Entity) other;
        return e.getUUID().equals(uuid);
    }
}
