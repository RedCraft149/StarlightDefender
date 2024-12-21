package com.redcraft.starlight.client.control;

import com.redcraft.starlight.client.input.Input;
import com.redcraft.starlight.client.rendering.RenderSystem;

import java.util.ArrayList;
import java.util.List;

public class ControllerGroup extends Controller {
    List<Controller> controllers;

    public ControllerGroup(PlayerControlInterface playerControlInterface) {
        super(playerControlInterface);
        controllers = new ArrayList<>();
    }

    public void addController(Controller controller) {
        this.controllers.add(controller);
    }

    @Override
    public void reset() {
        for(Controller controller : controllers) controller.reset();
    }

    @Override
    public void process(Input input) {
        for(Controller controller : controllers) controller.process(input);
    }

    @Override
    public void tick(float dt) {
        for(Controller controller : controllers) controller.tick(dt);
    }
    @Override
    public void render(RenderSystem system) {
        system.begin(RenderSystem.SPRITES);
        for(Controller controller : controllers) controller.render(system);
        system.sprites().flushAll();
        system.renderAllSprites();
        system.end(RenderSystem.SPRITES);
    }
    @Override
    public void dispose() {
        for(Controller controller : controllers) controller.dispose();
    }
}
