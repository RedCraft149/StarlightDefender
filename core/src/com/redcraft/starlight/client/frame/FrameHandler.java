package com.redcraft.starlight.client.frame;

import com.redcraft.starlight.client.input.InputCache;
import com.redcraft.starlight.client.rendering.RenderSystem;

import java.util.HashMap;
import java.util.Map;

public class FrameHandler {
    Map<String, Frame> frames;
    public FrameHandler() {
        frames = new HashMap<>();
    }
    public Frame add(String name, Frame frame) {
        frames.put(name, frame);
        return frame;
    }
    public Frame get(String name) {
        return frames.get(name);
    }
    public void loop(float dt, RenderSystem renderSystem, InputCache inputCache) {
        for (Frame frame : frames.values()) {
            frame.loop(dt, renderSystem, inputCache);
        }
        inputCache.clear();
    }

    public void disposeAll() {
        for (Frame frame : frames.values()) {
            System.out.println("frame = "+frame);
            frame.dispose();
        }
        System.out.println("all frames disposed");
        frames.clear();
    }
}
