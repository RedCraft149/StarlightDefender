package com.redcraft.rlib.events.fields;

import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class FloatEventField extends EventField<Float>{
    public FloatEventField(float value, String name, UUID owner, EventHandler eventHandler) {
        super(value, name, owner, eventHandler);
    }

    public void add(float d) {
        this.set(value+d);
    }
    public void multiply(float d) {
        this.set(value*d);
    }

    public void addSilent(float d) {
        this.setSilent(value+d);
    }
    public void multiplySilent(float d) {
        this.setSilent(value*d);
    }

}
