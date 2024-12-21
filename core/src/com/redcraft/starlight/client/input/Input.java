package com.redcraft.starlight.client.input;

import com.redcraft.rlib.function.Consumer;

public class Input {

    public static final int KEY_DOWN = 1;
    public static final int KEY_UP = 2;
    public static final int KEY_TYPED = 4;
    public static final int TOUCH_DOWN = 8;
    public static final int TOUCH_UP = 16;
    public static final int TOUCH_DRAGGED = 32;
    public static final int MOUSE_MOVED = 64;
    public static final int SCROLLED = 128;

    public final int x, y;
    public final int pointer;
    public final int button;
    public final int key;
    public final char character;
    public final int type;
    public final float amountX, amountY;

    private Input(int x, int y, int pointer, int button, int key, char character, float amountX, float amountY, int type) {
        this.x = x;
        this.y = y;
        this.pointer = pointer;
        this.button = button;
        this.key = key;
        this.character = character;
        this.type = type;
        this.amountX = amountX;
        this.amountY = amountY;
    }

    private Input(int x, int y, int pointer, int button, int key, char character, int type) {
        this(x, y, pointer, button, key, character, 0f, 0f, type);
    }

    private Input(float amountX, float amountY) {
        this(0, 0, 0, 0, 0, '\0', amountX, amountY, SCROLLED);
    }

    public boolean isOfType(int type) {
        return (this.type & type) != 0;
        //return this.type == type;
    }

    public static Input keyDown(int key) {
        return new Input(0, 0, 0, 0, key, '\0', 0f, 0f, KEY_DOWN);
    }
    public static Input keyUp(int key) {
        return new Input(0, 0, 0, 0, key, '\0', 0f, 0f, KEY_UP);
    }
    public static Input keyTyped(char character) {
        return new Input(0, 0, 0, 0, 0, character, 0f, 0f, KEY_TYPED);
    }
    public static Input touchDown(int x, int y, int pointer, int button) {
        return new Input(x, y, pointer, button, 0, '\0', 0f, 0f, TOUCH_DOWN);
    }
    public static Input touchUp(int x, int y, int pointer, int button) {
        return new Input(x, y, pointer, button, 0, '\0', 0f, 0f, TOUCH_UP);
    }
    public static Input touchDragged(int x, int y, int pointer) {
        return new Input(x, y, pointer, 0, 0, '\0', 0f, 0f, TOUCH_DRAGGED);
    }
    public static Input mouseMoved(int x, int y) {
        return new Input(x, y, 0, 0, 0, '\0', 0f, 0f, MOUSE_MOVED);
    }
    public static Input scrolled(float amountX, float amountY) {
        return new Input(amountX, amountY);
    }

    public static boolean onKeyDown(Input input, Consumer<Input> action) {
        if(input.type==KEY_DOWN) {
            action.accept(input);
            return true;
        }
        return false;
    }

    public static boolean onKeyUp(Input input, Consumer<Input> action) {
        if(input.type==KEY_UP) {
            action.accept(input);
            return true;
        }
        return false;
    }

    public static boolean onKeyTyped(Input input, Consumer<Input> action) {
        if(input.type==KEY_TYPED) {
            action.accept(input);
            return true;
        }
        return false;
    }

    public static boolean onTouchDown(Input input, Consumer<Input> action) {
        if(input.type==TOUCH_DOWN) {
            action.accept(input);
            return true;
        }
        return false;
    }

    public static boolean onTouchUp(Input input, Consumer<Input> action) {
        if(input.type==TOUCH_UP) {
            action.accept(input);
            return true;
        }
        return false;
    }

    public static boolean onTouchDragged(Input input, Consumer<Input> action) {
        if(input.type==TOUCH_DRAGGED) {
            action.accept(input);
            return true;
        }
        return false;
    }

    public static boolean onMouseMoved(Input input, Consumer<Input> action) {
        if(input.type==MOUSE_MOVED) {
            action.accept(input);
            return true;
        }
        return false;
    }

    public static boolean onScrolled(Input input, Consumer<Input> action) {
        if(input.type==SCROLLED) {
            action.accept(input);
            return true;
        }
        return false;
    }

    public static boolean onEvent(Input input, int type, Consumer<Input> action) {
        if(input.type==type) {
            action.accept(input);
            return true;
        }
        return false;
    }

}
