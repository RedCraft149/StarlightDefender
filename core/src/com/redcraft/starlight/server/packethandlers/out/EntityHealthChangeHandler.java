package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.shared.events.HealthChangeEvent;
import com.redcraft.starlight.shared.packets.EntityHealthPacket;
import com.redcraft.rlib.events.EventListener;

public class EntityHealthChangeHandler extends ServerListener {
    @EventListener
    public void onHealthChange(HealthChangeEvent event) {
        if(!(event.getEntity() instanceof SPlayer)) return;
        send(event, new EntityHealthPacket(event.getEntity().getUUID(), event.getNewHealth()));
    }
}
