package com.redcraft.starlight.client.input;

public interface InputHandler {
    void process(Input input);
    default void tick(float dt) {};
}
