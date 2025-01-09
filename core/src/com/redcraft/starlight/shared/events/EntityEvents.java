package com.redcraft.starlight.shared.events;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.shared.Components;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.rlib.events.EventHandler;

public class EntityEvents {
    public static void positionChanged(Entity entity, float toX, float toY, EventHandler eventHandler) {
        if(eventHandler==null) eventHandler = Components.getResponsibleEventHandler(entity.getComponents());
        if(eventHandler==null) return;

        if(!Components.throwPositionEvents(entity.getComponents())) return;
        eventHandler.throwEvent(
                new EntityPositionChangedEvent(entity.getPosition(),new Vector2(toX ,toY),entity)
        );
    }
    public static void positionChanged(Entity entity, float toX, float toY, boolean force, EventHandler eventHandler) {
        if(eventHandler==null) eventHandler = Components.getResponsibleEventHandler(entity.getComponents());
        if(eventHandler==null) return;

        if(!Components.throwPositionEvents(entity.getComponents())) return;
        eventHandler.throwEvent(
                new EntityPositionChangedEvent(entity.getPosition(),new Vector2(toX ,toY),entity,force)
        );
    }

    public static void rotationChanged(Entity entity, float toRad, EventHandler eventHandler) {
        if(eventHandler==null) eventHandler = Components.getResponsibleEventHandler(entity.getComponents());
        if(eventHandler==null) return;

        if(!Components.throwRotationEvents(entity.getComponents())) return;
        eventHandler.throwEvent(
                new EntityRotationChangedEvent(entity,toRad)
        );
    }

    public static void removed(Entity entity, EventHandler eventHandler) {
        if(eventHandler==null) eventHandler = Components.getResponsibleEventHandler(entity.getComponents());
        if(eventHandler==null) return;

        if(!Components.throwRemoveEvents(entity.getComponents())) return;
        eventHandler.throwEvent(
                new EntityRemoveEvent(entity)
        );
    }
}
