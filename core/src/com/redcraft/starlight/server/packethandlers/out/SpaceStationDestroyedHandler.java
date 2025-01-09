package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.QuickEvent;
import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.shared.packets.PlayerPausePacket;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;

import java.util.HashSet;

public class SpaceStationDestroyedHandler extends ServerListener {
    @EventListener
    public void onSpaceStationDestroyed(QuickEvent event) {
        if(!event.matches("space_station_destroyed")) return;
        System.out.println("SEND EVENT");
        for(SPlayer player : (HashSet<SPlayer>) event.read("players", HashSet.class)) {
            server.send(StarlightDefenderPacketList.newPacket("space_station_destroyed").set("all",true),player.getUUID());
        }
    }


}
