package com.redcraft.starlight.server.world;

import com.badlogic.gdx.math.MathUtils;
import com.redcraft.starlight.server.elements.SStaticObject;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.world.Chunk;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.starlight.util.RMath;

import java.util.Objects;
import java.util.UUID;

public class ChunkPopulator {

    private static final int[] OBJECT_TABLE = {1, 1, 1, 2};

    public void populate(Universe universe, int num, int chunkIndex) {
        int x = universe.chunkXFromIndex(chunkIndex);
        int y = universe.chunkYFromIndex(chunkIndex);
        Chunk chunk = universe.getChunk(chunkIndex);
        for(int idx = 0; idx < num; idx++) {
            UUID uuid = UUID.randomUUID();
            Entity entity = new SStaticObject(uuid, pickRandomType());
            entity.setPosition(x+ RMath.random(1/16f),y+RMath.random(1/16f));
            chunk.addEntity(entity);
        }
    }

    private int pickRandomType() {
        return OBJECT_TABLE[MathUtils.random(OBJECT_TABLE.length-1)];
    }
}
