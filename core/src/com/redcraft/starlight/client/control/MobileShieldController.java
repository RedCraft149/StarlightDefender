package com.redcraft.starlight.client.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.input.Input;

public class MobileShieldController extends Controller{

    final Rectangle shieldGUIBox;

    final Vector2 down;
    int pointer;

    public MobileShieldController(PlayerControlInterface playerControlInterface) {
        super(playerControlInterface);
        down = new Vector2();
        shieldGUIBox = new Rectangle(0,0, Gdx.graphics.getWidth()*0.25f,100);
    }

    @Override
    public void reset() {
        pointer = -1;
        down.set(0,0);
    }

    @Override
    public void process(Input input) {
        if(input.isOfType(Input.TOUCH_DOWN) && shieldGUIBox.contains(input.x,input.y)) {
            down.set(input.x,input.y);
            pointer = input.pointer;
        }
        if(input.isOfType(Input.TOUCH_UP) && input.pointer == pointer) {
            pointer = -1;
            if(down.dst(input.x,input.y) < 50) activateShield();
        }
        if(input.isOfType(Input.KEY_DOWN)) {
            if(input.key == com.badlogic.gdx.Input.Keys.SHIFT_LEFT) activateShield();
        }
    }
    public void activateShield() {
        System.out.println("ACTIVATE SHIELD");
        control.raiseShields();
    }
}
