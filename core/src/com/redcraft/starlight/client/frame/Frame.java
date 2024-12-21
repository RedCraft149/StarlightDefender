package com.redcraft.starlight.client.frame;

import com.redcraft.starlight.client.input.InputCache;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.rlib.Castable;

public abstract class Frame implements Castable<Frame> {

    public static final int TICK = 1;
    public static final int PROCESS_INPUT = 2;
    public static final int RENDER = 4;

    private int currentState;
    protected final FrameHandler frameHandler;

    public Frame(FrameHandler frameHandler) {
        this.frameHandler = frameHandler;
    }

    public void loop(float dt, RenderSystem renderSystem, InputCache inputCache) {
        if((currentState & PROCESS_INPUT)!=0) {
            processInputs(inputCache,dt);
        }
        if((currentState & TICK)!=0) step(dt);
        if((currentState & RENDER)!=0) render(renderSystem);
    }
    public void enable(int state) {
        currentState |= state;
    }
    public void disable(int state) {
        currentState &= ~state;
    }
    public void pause() {
        currentState = RENDER;
    }
    public void resume() {
        currentState = TICK | PROCESS_INPUT | RENDER;
    }
    public void hide() {
        currentState = 0;
    }

    public int state() {
        return currentState;
    }

    public boolean enabled(int state) {
        return (currentState & state) != 0;
    }

    public abstract void create();
    public abstract void processInputs(InputCache inputs, float dt);
    public abstract void step(float deltaTime);
    public abstract void render(RenderSystem renderSystem);
    public abstract void dispose();
}
