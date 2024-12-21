package com.redcraft.starlight.client.control;

import com.badlogic.gdx.utils.Disposable;
import com.redcraft.starlight.client.input.InputHandler;
import com.redcraft.starlight.client.rendering.RenderSystem;

public abstract class Controller implements InputHandler , Disposable {
    protected PlayerControlInterface control;

    public Controller(PlayerControlInterface playerControlInterface) {
        this.control = playerControlInterface;
    }

    public abstract void reset();
    public void render(RenderSystem system) {}
    public void dispose() {}

}
