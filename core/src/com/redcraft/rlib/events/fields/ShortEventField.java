package com.redcraft.rlib.events.fields;

import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class ShortEventField extends EventField<Short> {
    public ShortEventField(short value, String name, UUID owner, EventHandler eventHandler) {
        super(value, name, owner, eventHandler);
    }

    public void add(short d) {
        this.set((short) (value+d));
    }
    public void multiply(short d) {
        this.set((short) (value*d));
    }

    public void addSilent(short d) {
        this.setSilent((short) (value+d));
    }
    public void multiplySilent(short d) {
        this.setSilent((short) (value*d));
    }
}
