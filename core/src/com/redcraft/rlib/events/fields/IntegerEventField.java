package com.redcraft.rlib.events.fields;

import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class IntegerEventField extends EventField<Integer>{
    public IntegerEventField(int value, String name, UUID owner, EventHandler eventHandler) {
        super(value, name, owner, eventHandler);
    }

    public void add(int d) {
        this.set(value+d);
    }
    public void multiply(int d) {
        this.set(value*d);
    }
    public void divide(int d) {
        this.set(value/d);
    }

    public void addSilent(int d) {
        this.setSilent(value+d);
    }
    public void multiplySilent(int d) {
        this.setSilent(value*d);
    }
    public void divideSilent(int d) {
        this.setSilent(value/d);
    }
}
