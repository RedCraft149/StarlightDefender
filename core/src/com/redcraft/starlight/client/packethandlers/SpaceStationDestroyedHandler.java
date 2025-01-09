package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.GSyncPacketReceiver;
import com.redcraft.communication.packets.handlers.Receiver;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.events.QuickEvent;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.effects.SoundSystem;
import com.redcraft.starlight.shared.Shared;

public class SpaceStationDestroyedHandler extends GSyncPacketReceiver {
    public SpaceStationDestroyedHandler() {
        super("space_station_destroyed");
        setTarget(this);
    }
    @Receiver(fields = {"all"})
    public void handle(boolean all) {
        System.out.println("DESTROYED!!!");
        Shared.CLIENT.get(CComponents.soundSystem, SoundSystem.class).play("space_station_destroyed",1f);
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).throwEvent(new QuickEvent("space_station_destroyed"));
    }
}
