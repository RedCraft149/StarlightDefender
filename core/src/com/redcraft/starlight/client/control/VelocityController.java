package com.redcraft.starlight.client.control;

import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.shared.Shared;

public abstract class VelocityController extends Controller {

    protected float velocity;
    protected boolean move;

    public VelocityController(PlayerControlInterface playerControlInterface) {
        super(playerControlInterface);
    }

    @Override
    public void reset() {
        move = false;
        velocity = 0f;
    }

    public void move(boolean move) {
        this.move = move;
    }

    @Override
    public void tick(float dt) {
        Shared.CLIENT.set(CComponents.worldScale,velocity*velocity*0.5f - 0.25f);
        if(move) {
            if(velocity<0.7f) velocity += 0.01f;
            control.moveInFacingDirection(velocity*dt);
        } else {
            if(velocity>0.01f) {
                velocity -= 0.01f;
                control.moveInFacingDirection(velocity*dt);
            } else {
                velocity = 0f;
            }
        }
    }
}
