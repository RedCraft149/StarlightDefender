package com.redcraft.starlight.shared.entity;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.shared.world.Chunk;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.starlight.util.RMath;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class EntityFinder {

    public static int CHUNK_NOT_FOUND = -1;

    Universe universe;
    Map<UUID,Entity> registeredUUIDs;

    public EntityFinder(Universe universe) {
        this.universe = universe;
        this.registeredUUIDs = new HashMap<>();
    }

    /**
     * Finds the chunk the specified entity lives in. This method will only fail if
     * the entity does not exist in any chunk of the universe, but may vary in
     * runtime depending on the match between the entity's position and its containing chunk.
     * @param entity The entity to find the containing chunk for.
     * @return The index of the containing chunk. A {@link Chunk} object can be retrieved by calling
     *         {@link Universe#getChunk(int)}.
     */
    public int containingChunk(Entity entity) {
        //Try finding the entity by its position
        Vector2 pos = entity.getPosition();
        int x = universe.chunkX(pos.x);
        int y = universe.chunkY(pos.y);
        if(universe.getChunk(x,y).containsEntity(entity.getUUID())) return universe.getChunkIndex(x,y);

        //Looking into the chunks around the position of the entity in case it has moved, but was not redistributed yet
        for(int lx = x-1; lx <= x+1; lx++) {
            for(int ly = y-1; ly <= y+1; ly++) {
                int rx = RMath.mod(lx,universe.width());
                int ry = RMath.mod(ly,universe.height());
                if(universe.getChunk(rx,ry).containsEntity(entity.getUUID())) return universe.getChunkIndex(rx,ry);
            }
        }

        //Search the whole universe for the entity
        for(int rx = 0; rx < universe.width(); rx++) {
            for(int ry = 0; ry < universe.height(); ry++) {
                if(universe.getChunk(rx,ry).containsEntity(entity.getUUID())) return universe.getChunkIndex(rx,ry);
            }
        }

        //Entity does not exist
        return CHUNK_NOT_FOUND;
    }

    /**
     * Registers an entity to this entity finder's entity list, improving performance of
     * {@link EntityFinder#searchEntity(UUID)}.
     * @param entity Entity to register.
     */
    public void registerEntity(Entity entity) {
        if(entity == null) throw new NullPointerException("Entity cannot be null!");
        registeredUUIDs.put(entity.getUUID(),entity);
    }

    public void removeRegisteredEntity(Entity entity) {
        registeredUUIDs.remove(entity.getUUID());
    }
    public void removeRegisteredUUID(UUID uuid) {
        registeredUUIDs.remove(uuid);
    }

    /**
     * @param uuid
     * @return The registered entity with the given UUID.
     */
    public Entity getEntity(UUID uuid) {
        return registeredUUIDs.get(uuid);
    }

    /**
     * Gets the entity with the specified UUID from this entity finder's entity list (faster),
     * or searches for an entity with the specified UUID in the universe (slower).
     * @param uuid UUID to search for.
     * @return The entity with the specified UUID, or null if there is none.
     */
    public Entity searchEntity(UUID uuid) {
        Entity e = getEntity(uuid);
        if(e!=null) return e;

        xloop:
        for(int rx = 0; rx < universe.width(); rx++) {
            for (int ry = 0; ry < universe.height(); ry++) {
                Chunk chunk = universe.getChunk(rx,ry);
                if(chunk.containsEntity(uuid)) {
                    e = chunk.getEntity(uuid);
                    registerEntity(e);
                    break xloop;
                }
            }
        }

        return e;
    }

    /**
     * Tries to find an entity with the specified UUID given a position where the entity <b>may</b> be.
     * In the case the given position does not match the entities containing chunk, {@link EntityFinder#searchEntity(UUID)}
     * is called.
     * @param uuid UUID to search for
     * @param potentialX x position where the entity may be
     * @param potentialY y position where the entity may be
     * @return The entity with the specified UUID, or null if there is none.
     */
    public Entity searchEntity(UUID uuid, float potentialX, float potentialY) {
        potentialX = RMath.mod(potentialX,universe.width());
        potentialY = RMath.mod(potentialY,universe.height());
        int chunk = universe.getChunkIndex(potentialX,potentialY);
        if(chunk==-1) return searchEntity(uuid);

        Entity e = universe.getChunk(chunk).getEntity(uuid);
        if(e!=null) return e;
        return searchEntity(uuid);
    }

}
