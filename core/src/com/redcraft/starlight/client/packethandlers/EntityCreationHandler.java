package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.GPolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.GSyncPacketReceiver;
import com.redcraft.communication.packets.handlers.Receiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.*;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entities;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.events.QuickEvent;

import java.util.UUID;

public class EntityCreationHandler extends GPolySyncPacketReceiver {
    public EntityCreationHandler() {
        super("entity_creation");
        setTarget(this);
    }

    @Receiver(fields={"x","y","rotation","id","type"})
    public void handle(float x, float y, float rotation, UUID uuid, int type) {
        Universe universe = Shared.CLIENT.get(CComponents.universe, Universe.class);

        CEntity e = Entities.clientEntityFromType(type,uuid);
        if(e!=null) {
            e.setPosition(x,y);
            e.setRotation(rotation);
            universe.entityDistributor().addEntity(e);
        }

        if(e instanceof CPlayer) {
            Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class)
                    .throwEvent(new QuickEvent("player_join").set("entity", e));
        }

        System.out.println("added entity: "+x+","+y+","+uuid+","+type);
    }


}
