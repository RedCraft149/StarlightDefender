package com.redcraft.rlib.events.fields;

import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class ByteEventField extends EventField<Byte> {
    public ByteEventField(byte value, String name, UUID owner, EventHandler eventHandler) {
        super(value, name, owner, eventHandler);
    }

    public void add(byte d) {
        this.set((byte) (value+d));
    }
    public void multiply(byte d) {
        this.set((byte) (value*d));
    }

    public void addSilent(byte d) {
        this.setSilent((byte) (value+d));
    }
    public void multiplySilent(byte d) {
        this.setSilent((byte) (value*d));
    }
}
