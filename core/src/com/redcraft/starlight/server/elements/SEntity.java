package com.redcraft.starlight.server.elements;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.server.SConstants;
import com.redcraft.starlight.shared.Components;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.ModularEntity;
import com.redcraft.rlib.events.Event;
import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class SEntity extends ModularEntity {
    public SEntity(UUID uuid) {
        super(uuid, Shared.SERVER.get(SConstants.universeDimensions, Vector2.class),true);
        set(Components.RESPONSIBLE_EVENT_HANDLER, Shared.SERVER.get(SConstants.gameEventHandler));
        setFlag(Components.THROW_POSITION_EVENTS);
        setFlag(Components.THROW_ROTATION_EVENTS);
        setFlag(Components.THROW_REMOVE_EVENTS);
    }

    public void throwEvent(Event event) {
        Components.getResponsibleEventHandler(getComponents()).throwEvent(event);
    }
    public EventHandler getEventHandler() {
        return Components.getResponsibleEventHandler(getComponents());
    }
}
