package com.redcraft.starlight.client.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.ApplicationHook;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CChunk;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.client.input.Input;
import com.redcraft.starlight.launcher.HomeScreen;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.world.Chunk;

public class DesktopController extends VelocityController{

    public DesktopController(PlayerControlInterface playerControlInterface) {
        super(playerControlInterface);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void process(Input input) {
        if(input.isOfType(Input.MOUSE_MOVED | Input.TOUCH_DRAGGED)) handleMove(input);
        if(input.isOfType(Input.TOUCH_DOWN)) handleClick();
        if(input.isOfType(Input.KEY_DOWN)) handleKeyDown(input);
        if(input.isOfType(Input.KEY_UP)) handleKeyUp(input);
    }

    protected void handleMove(Input input) {
        Vector2 pivot = new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()).scl(0.5f);
        float rad = (float) Math.atan2(input.y-pivot.y,input.x-pivot.x) + MathUtils.HALF_PI;
        control.rotate(rad);
    }
    private void handleClick() {
        control.shoot();
    }
    private void handleKeyDown(Input input) {
        if(input.key == com.badlogic.gdx.Input.Keys.W) move(true);
        if(input.key == com.badlogic.gdx.Input.Keys.SHIFT_LEFT) control.raiseShields();
        if(input.key == com.badlogic.gdx.Input.Keys.ESCAPE) {
            Gdx.app.postRunnable(()->ApplicationHook.get().switchTo(new HomeScreen()));
        }
        if(input.key == com.badlogic.gdx.Input.Keys.F3) {
            CChunk.DEBUG = !CChunk.DEBUG;
        }
        if(input.key == com.badlogic.gdx.Input.Keys.F4){
            debugChunk();
        }
    }

    private void debugChunk() {
        CUniverse universe = Shared.CLIENT.get(CComponents.universe,CUniverse.class);
        int index = universe.getChunkIndex(universe.getPlayer().getPosition());
        Chunk chunk = universe.getChunk(index);
        for(Entity e : chunk.getEntities()) System.out.println(e.toString());
    }

    private void handleKeyUp(Input input) {
        if(input.key == com.badlogic.gdx.Input.Keys.W) move(false);
    }
}
