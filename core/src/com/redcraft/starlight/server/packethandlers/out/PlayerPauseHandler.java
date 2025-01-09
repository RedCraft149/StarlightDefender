package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.rlib.events.QuickEvent;
import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.shared.events.PlayerDeathEvent;
import com.redcraft.starlight.shared.packets.EntityPositionPacket;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.starlight.shared.packets.PlayerPausePacket;

public class PlayerPauseHandler extends ServerListener {
    @EventListener
    public void onPlayerDeath(PlayerDeathEvent event) {
        send(event,new PlayerPausePacket("You crashed!",false,false));
        event.getEntity().setPosition(0f,0f);
        send(event,new EntityPositionPacket(0f,0f,event.getEntity().getUUID()));
    }

    @EventListener
    public void onPlayerPause(QuickEvent event) {
        if(!event.matches("player_pause")) return;
        server.send(new PlayerPausePacket("level_cleared",false,true),event.read("player", SPlayer.class).getUUID());
    }
}
