package com.redcraft.rlib.events.fields;

import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class BooleanEventField extends EventField<Boolean>{
    public BooleanEventField(boolean value, String name, UUID owner, EventHandler eventHandler) {
        super(value, name, owner, eventHandler);
    }
}
