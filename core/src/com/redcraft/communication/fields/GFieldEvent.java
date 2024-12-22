package com.redcraft.communication.fields;

import com.redcraft.rlib.events.Event;

public class GFieldEvent implements Event {
    private GField<?> field;

    public GFieldEvent(GField<?> field) {
        this.field = field;
    }

    public GField<?> getField() {
        return field;
    }
}
