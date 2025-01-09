package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.shared.events.PlayerPauseTimerChangeEvent;
import com.redcraft.starlight.shared.packets.PlayerPausePacket;
import com.redcraft.starlight.shared.packets.PauseTimerPacket;
import com.redcraft.rlib.events.EventListener;

public class PlayerPauseTimerChangeHandler extends ServerListener {
    @EventListener
    public void onTimerChange(PlayerPauseTimerChangeEvent event) {
        if(event.timer() <= 0f) {
            event.getEntity().as(SPlayer.class).respawn();
            send(event,new PlayerPausePacket("respawn",true,false));
            System.out.println("respawn");
        } else {
            send(event,new PauseTimerPacket(event.timer()));
        }
    }
}
