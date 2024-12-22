package com.redcraft.rlib.events.fields;

import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class LongEventField extends EventField<Long> {
    public LongEventField(long value, String name, UUID owner, EventHandler eventHandler) {
        super(value, name, owner, eventHandler);
    }

    public void add(long d) {
        this.set(value+d);
    }
    public void multiply(long d) {
        this.set(value*d);
    }

    public void addSilent(long d) {
        this.setSilent(value+d);
    }
    public void multiplySilent(long d) {
        this.setSilent(value*d);
    }
}
