package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.shared.events.EntityPositionChangedEvent;
import com.redcraft.starlight.shared.packets.EntityPositionPacket;
import com.redcraft.rlib.events.EventListener;

public class EntityPositionHandler extends ServerListener {
    @EventListener
    public void onEntityMove(EntityPositionChangedEvent event) {
        sendExcluding(event,new EntityPositionPacket(event.to().x, event.to().y, event.getEntity().getUUID()));
    }
}
