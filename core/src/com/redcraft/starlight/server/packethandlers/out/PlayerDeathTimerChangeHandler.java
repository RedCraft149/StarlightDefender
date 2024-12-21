package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.shared.events.PlayerDeathTimerChangeEvent;
import com.redcraft.starlight.shared.packets.PlayerDeathPacket;
import com.redcraft.starlight.shared.packets.RespawnTimerPacket;
import com.redcraft.rlib.events.EventListener;

public class PlayerDeathTimerChangeHandler extends ServerListener {
    @EventListener
    public void onTimerChange(PlayerDeathTimerChangeEvent event) {
        if(event.deathTimer() <= 0f) {
            event.getEntity().as(SPlayer.class).respawn();
            send(event,new PlayerDeathPacket("respawn",true));
            System.out.println("respawn");
        } else {
            send(event,new RespawnTimerPacket(event.deathTimer()));
        }
    }
}
