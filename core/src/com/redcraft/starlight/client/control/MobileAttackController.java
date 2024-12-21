package com.redcraft.starlight.client.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.input.Input;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.util.Files;

public class MobileAttackController extends Controller{

    final Vector2 center, down;
    final float size;
    int pointer = -1;

    Texture texture;

    public MobileAttackController(PlayerControlInterface playerControlInterface) {
        super(playerControlInterface);
        center = new Vector2(Gdx.graphics.getWidth()*(0.1f),Gdx.graphics.getHeight()*0.2f);
        down = new Vector2();
        size = Math.min(Gdx.graphics.getWidth(),Gdx.graphics.getHeight())*0.5f*0.1f;
        texture = Files.texture("button");
    }

    @Override
    public void reset() {
        pointer = -1;
    }

    @Override
    public void process(Input input) {
        if(input.isOfType(Input.KEY_DOWN)) {
            if(input.key == com.badlogic.gdx.Input.Keys.SPACE) control.shoot();
        }
        if(center.dst(input.x,input.y) > size*3) return;
        if(center.x > Gdx.graphics.getWidth() * 0.5f) return;
        if(input.isOfType(Input.TOUCH_DOWN) && pointer == -1) {
            down.set(input.x,input.y);
            pointer = input.pointer;
        }

        if(input.isOfType(Input.TOUCH_UP)) {
            if(input.pointer != pointer) return;
            if(down.dst(input.x,input.y) < 50f) control.shoot();
            pointer = -1;
        }
        if(input.isOfType(Input.TOUCH_DRAGGED)) {
            if(input.pointer != pointer) return;
            center.set(input.x,input.y);
        }
    }

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
