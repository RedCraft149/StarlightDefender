package com.redcraft.starlight.shared.world;

import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.rlib.components.ComponentSystemAccessor;
import com.redcraft.rlib.Castable;
import com.redcraft.rlib.keyless.KeylessMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Chunk extends ComponentSystemAccessor implements Castable<Chunk> {
    KeylessMap<UUID, Entity> entities;
    protected int x, y;

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;
        entities = new KeylessMap<>();
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }
    public void removeEntity(Entity e) {
        entities.remove(e.getUUID());
    }
    public void removeEntity(UUID uuid) {
        entities.remove(uuid);
    }
    public <E extends Entity> E getEntity(UUID uuid) {
        try {
            @SuppressWarnings("unchecked")
            E e = (E) entities.get(uuid);
            return e;
        } catch (ClassCastException ex) {
            return null;
        }
    }
    public <E extends Entity> E getEntity(UUID uuid, Class<E> type) {
        Entity entity = entities.get(uuid);
        if(!type.isInstance(entity)) return null;
        return type.cast(entity);
    }
    public boolean containsEntity(UUID uuid) {
        return entities.containsKey(uuid);
    }

    public KeylessMap<UUID,Entity> entities() {
        return entities;
    }
    public Collection<Entity> getEntities() {
        return entities.values();
    }
    public <E extends Entity> Collection<E> getEntities(Class<E> type) {
        Collection<E> collection = new HashSet<>();
        for(Entity e : getEntities()) {
            if(type.isInstance(e)) collection.add(type.cast(e));
        }
        return collection;
    }
    public void clear() {
        this.entities.clear();
    }

    public String toString() {
        return "Chunk("+x+"|"+y+")";
    }
}
