package com.redcraft.starlight.client.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.input.Input;

public class EasyMobileController extends Controller{

    private boolean move = false;
    private float velocity = 0f;
    private boolean dragged;
    private final Vector2 down;

    public EasyMobileController(PlayerControlInterface playerControlInterface) {
        super(playerControlInterface);
        down = new Vector2();
    }

    @Override
    public void reset() {
        move = false;
        velocity = 0f;
        dragged = false;
        down.set(0f,0f);
    }

    @Override
    public void process(Input input) {
        if(input.isOfType(Input.TOUCH_DRAGGED)) onDragged(input);
        if(input.isOfType(Input.TOUCH_DOWN)) onTouchDown(input);
        if(input.isOfType(Input.TOUCH_UP)) onTouchUp(input);
    }

    private void onDragged(Input input) {
        Vector2 pivot = new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()).scl(0.5f);
        float rad = (float) Math.atan2(input.y-pivot.y,input.x-pivot.x) + MathUtils.HALF_PI;
        control.rotate(rad);

        dragged = true;
    }

    private void onTouchDown(Input input) {
        down.set(input.x,input.y);
        move = true;

        Vector2 pivot = new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()).scl(0.5f);
        float rad = (float) Math.atan2(input.y-pivot.y,input.x-pivot.x) + MathUtils.HALF_PI;
        control.rotate(rad);
    }

    private void onTouchUp(Input input) {
        move = false;
        if(dragged && down.dst(input.x,input.y) > 10) return;
        control.shoot();
    }



    public void tick(float dt) {
        if(move) {
            if(velocity<0.02f) velocity += 0.001f;
            control.moveInFacingDirection(velocity*dt*60);
        } else {
            if(velocity>0.001f) {
                velocity -= 0.001f;
                control.moveInFacingDirection(velocity*dt*60);
            } else {
                velocity = 0f;
            }
        }
    }
}
