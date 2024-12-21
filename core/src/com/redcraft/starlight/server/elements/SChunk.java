package com.redcraft.starlight.server.elements;

import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.world.Chunk;
import com.redcraft.rlib.Processable;

public class SChunk extends Chunk implements Processable {
    public SChunk(int x, int y) {
        super(x, y);
    }

    @Override
    public void process(float dt) {
        for(Entity e : getEntities()) {
            if(e.is(Processable.class)) e.to(Processable.class).process(dt);
        }
    }
}
