package com.redcraft.starlight.client.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.input.Input;

public class DesktopControllerCatchedCursor extends DesktopController{

    Vector2 currentDirection;
    float sensitivity = 0.01f;

    public DesktopControllerCatchedCursor(PlayerControlInterface playerControlInterface) {
        super(playerControlInterface);
        Gdx.input.setCursorCatched(true);
        currentDirection = new Vector2(0,0);
    }

    @Override
    protected void handleMove(Input input) {
        currentDirection.add(Gdx.input.getDeltaX()*sensitivity,-Gdx.input.getDeltaY()*sensitivity);
        currentDirection.nor();
        float rotation = currentDirection.angleRad() + MathUtils.PI * 0.5f;
        control.rotate(rotation);
    }
}
