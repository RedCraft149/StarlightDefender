package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.shared.entity.Entities;
import com.redcraft.starlight.shared.events.EntityAddEvent;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.rlib.events.EventListener;

public class EntityAddHandler extends ServerListener {
    @EventListener
    public void onEntityAdd(EntityAddEvent event) {
        sendExcluding(event,
                StarlightDefenderPacketList.newPacket("entity_creation")
                        .set("id",event.getEntity().getUUID())
                        .set("x",event.getEntity().getPosition().x)
                        .set("y",event.getEntity().getPosition().y)
                        .set("rotation",event.getEntity().getRotation())
                        .set("type",Entities.typeServer(event.getEntity()))
        );
    }
}
