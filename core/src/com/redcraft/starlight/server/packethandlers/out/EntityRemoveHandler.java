package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.shared.events.EntityRemoveEvent;
import com.redcraft.starlight.shared.packets.EntityRemovePacket;
import com.redcraft.rlib.events.EventListener;

public class EntityRemoveHandler extends ServerListener {
    @EventListener
    public void onEntityRemove(EntityRemoveEvent event) {
        send(new EntityRemovePacket(event.getEntity().getUUID(),event.getEntity().getPosition().x,event.getEntity().getPosition().y));
    }
}
