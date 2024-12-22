package com.redcraft.rlib.events.fields;

import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class DoubleEventField extends EventField<Double> {
    public DoubleEventField(double value, String name, UUID owner, EventHandler eventHandler) {
        super(value, name, owner, eventHandler);
    }

    public void add(double d) {
        this.set(value+d);
    }
    public void multiply(double d) {
        this.set(value*d);
    }

    public void addSilent(double d) {
        this.setSilent(value+d);
    }
    public void multiplySilent(double d) {
        this.setSilent(value*d);
    }
}
