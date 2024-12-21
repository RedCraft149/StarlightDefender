package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.shared.events.PlayerDeathEvent;
import com.redcraft.starlight.shared.packets.EntityPositionPacket;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.starlight.shared.packets.PlayerDeathPacket;

public class PlayerDeathHandler extends ServerListener {
    @EventListener
    public void onPlayerDeath(PlayerDeathEvent event) {
        send(event,new PlayerDeathPacket("You crashed!",false));
        event.getEntity().setPosition(0f,0f);
        send(event,new EntityPositionPacket(0f,0f,event.getEntity().getUUID()));
    }
}
