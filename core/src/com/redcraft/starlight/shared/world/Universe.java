package com.redcraft.starlight.shared.world;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.shared.entity.EntityDistributor;
import com.redcraft.starlight.shared.entity.EntityFinder;
import com.redcraft.rlib.Castable;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.starlight.util.RMath;

public class Universe implements Castable<Universe> {
    protected Chunk[] chunks;
    protected int width, height;
    protected float chunkWidth = 1f, chunkHeight = 1f;
    protected EntityDistributor entityDistributor;
    protected EntityFinder entityFinder;
    protected EventHandler eventHandler;

    public Universe(int width, int height, EventHandler eventHandler) {
        this.width = width;
        this.height = height;
        this.eventHandler = eventHandler;
        this.chunks = new Chunk[width*height];
        this.entityDistributor = new EntityDistributor(this);
        this.entityFinder = new EntityFinder(this);

        eventHandler.register(entityDistributor);
    }

    public Chunk getChunk(int x, int y) {
        return chunks[x*height+y];
    }
    public Chunk getChunk(int i) {
        return chunks[i];
    }
    public int getChunkIndex(float x, float y) {
        int rx = chunkX(x);
        int ry = chunkY(y);
        int i = rx*height + ry;
        return i >= 0 && i < width*height ? i : -1;
    }
    public int getChunkIndex(Vector2 pos) {
        return getChunkIndex(pos.x,pos.y);
    }
    public int getChunkIndex(int x, int y) {
        return x*height + y;
    }
    public int chunkX(float x) {
        return RMath.floor(x/chunkWidth);
    }
    public int chunkY(float y) {
        return RMath.floor(y/chunkHeight);
    }
    public int width() {
        return width;
    }
    public int height() {
        return height;
    }
    public int chunkXFromIndex(int i) {
        return i / height;
    }
    public int chunkYFromIndex(int i) {
        return i % height;
    }

    public EntityDistributor entityDistributor() {
        return entityDistributor;
    }
    public EntityFinder entityFinder() {
        return entityFinder;
    }
    public EventHandler eventHandler() {
        return eventHandler;
    }
}
