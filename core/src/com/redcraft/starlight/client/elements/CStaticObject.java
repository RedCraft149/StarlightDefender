package com.redcraft.starlight.client.elements;


import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.events.ElementDestroyEvent;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entities;
import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

public class CStaticObject extends CEntity{

    Type type;

    public CStaticObject(UUID uuid, Type type) {
        super(uuid);
        this.type = type;

        setTexture(this.type.texture);
        setScale(Shared.CLIENT.getFloat("staticObject.scale"));
        recalculateTransformMatrix();
    }
    public CStaticObject(UUID uuid, int type) {
        this(uuid,Type.of(type));
    }

    public Type getType() {
        return type;
    }

    @Override
    public void draw(RenderSystem system) {
        super.draw(system);
    }

    @Override
    public void remove() {
        super.remove();
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).throwEvent(new ElementDestroyEvent(this,8,true));
    }

    public enum Type {

        ROCK(Entities.ROCK,"rock"),
        MINE(Entities.MINE,"mine"),
        UNKNOWN(-1,null);

        final int id;
        final String texture;

        Type(int id, String texture) {
            this.id = id;
            this.texture = texture;
        }

        public int id() {
            return id;
        }

        public String texture() {
            return texture;
        }

        public static Type of(int id) {
            for(Type t : Type.values()) {
                if(t.id()==id) return t;
            }
            return UNKNOWN;
        }
    }
}
