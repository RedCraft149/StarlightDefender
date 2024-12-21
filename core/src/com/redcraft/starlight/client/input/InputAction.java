package com.redcraft.starlight.client.input;

import com.redcraft.rlib.function.Consumer;

public interface InputAction {
    void accept(Input input, Consumer<Input> action);
}
