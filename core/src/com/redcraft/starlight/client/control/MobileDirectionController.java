package com.redcraft.starlight.client.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.input.Input;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.util.Files;

public class MobileDirectionController extends VelocityController {
    final Vector2 center, current;
    int pointer;
    float size;

    Texture texture;

    public MobileDirectionController(PlayerControlInterface playerControlInterface) {
        super(playerControlInterface);
        current = new Vector2();
        center = new Vector2(Gdx.graphics.getWidth()*(0.9f),Gdx.graphics.getHeight()*0.2f);
        size = Math.min(Gdx.graphics.getWidth(),Gdx.graphics.getHeight())*0.5f*0.1f;
        texture = Files.texture("button");
    }

    @Override
    public void reset() {
        super.reset();
        pointer = -1;
    }

    @Override
    public void process(Input input) {
        if (input.isOfType(Input.TOUCH_UP)) {
            if(input.pointer == pointer) move(false);
        }

        if(input.x < Gdx.graphics.getWidth() * 0.5f) return;
        if(input.isOfType(Input.TOUCH_DOWN)) {
            center.set(input.x,input.y);
            pointer = input.pointer;
            move(true);
        }
        if(input.isOfType(Input.TOUCH_DRAGGED)) {
            onDragged(input);
        }
    }

    private void onDragged(Input input) {
        move(center.dst(input.x,input.y)>=size);
        float rad = (float) Math.atan2(input.y-center.y,input.x-center.x) + MathUtils.HALF_PI;
        control.rotate(rad);
    }

    @Override
    public void render(RenderSystem system) {
        system.begin(RenderSystem.OVERLAY);
        system.overlays().draw(texture,center.x - size,center.y - size,size*2f,size*2f);
        system.end(RenderSystem.OVERLAY);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
