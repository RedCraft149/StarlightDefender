package com.redcraft.starlight.client.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.redcraft.rlib.function.Consumer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InputCache implements InputProcessor {

    final List<Input> recentInputs;

    public InputCache() {
        this.recentInputs = new ArrayList<>();
    }

    public void consume(int type, List<Input> inputs) {
        Iterator<Input> iterator = recentInputs.iterator();
        while(iterator.hasNext()) {
            Input input = iterator.next();
            if(input.type == type) {
                inputs.add(input);
                iterator.remove();
            }
        }
    }
    public void get(int type, List<Input> inputs) {
        for(Input input : recentInputs) {
            if(input.type == type) {
                inputs.add(input);
            }
        }
    }
    public void forAll(Consumer<Input> action) {
        for(Input input : recentInputs) {
            action.accept(input);
        }
    }
    public void forAll(InputAction action, Consumer<Input> executor) {
        for(Input input : recentInputs) {
            action.accept(input,executor);
        }
    }
    public void removeInput(Input input) {
        recentInputs.remove(input);
    }
    public void clear() {
        recentInputs.clear();
    }

    @Override
    public boolean keyDown(int keycode) {
        recentInputs.add(Input.keyDown(keycode));
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        recentInputs.add(Input.keyUp(keycode));
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        recentInputs.add(Input.keyTyped(character));
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight()-screenY;
        recentInputs.add(Input.touchDown(screenX, screenY, pointer, button));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight()-screenY;
        recentInputs.add(Input.touchUp(screenX, screenY, pointer, button));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenY = Gdx.graphics.getHeight()-screenY;
        recentInputs.add(Input.touchDragged(screenX, screenY, pointer));
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screenY = Gdx.graphics.getHeight()-screenY;
        recentInputs.add(Input.mouseMoved(screenX, screenY));
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        recentInputs.add(Input.scrolled(amountX, amountY));
        return true;
    }
}
