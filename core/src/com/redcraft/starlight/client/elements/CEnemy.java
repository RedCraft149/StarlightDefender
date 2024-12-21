package com.redcraft.starlight.client.elements;

import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.events.ElementDestroyEvent;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entities;
import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class CEnemy extends CEntity{

    Type type;

    public CEnemy(UUID uuid, Type type) {
        super(uuid);

        this.type = type;
        setTexture(type.texture);
        setScale(.125f);

        recalculateTransformMatrix();
    }

    public void draw(RenderSystem system) {
        super.draw(system);
    }

    @Override
    public void remove() {
        super.remove();
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).throwEvent(new ElementDestroyEvent(this,16,true));
    }

    public enum Type {
        BASIC(Entities.ENEMY,"enemy1"),
        ADVANCED(Entities.ENEMY2,"enemy2");

        final int id;
        final String texture;

        Type(int id, String texture) {
            this.id = id;
            this.texture = texture;
        }
    }
}
