package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.shared.events.EntityRotationChangedEvent;
import com.redcraft.starlight.shared.packets.EntityRotationPacket;
import com.redcraft.rlib.events.EventListener;

public class EntityRotationHandler extends ServerListener {
    @EventListener
    public void onEntityRotate(EntityRotationChangedEvent event) {
        sendExcluding(event,new EntityRotationPacket(event.toRad(), event.getEntity().getUUID()));
    }
}
