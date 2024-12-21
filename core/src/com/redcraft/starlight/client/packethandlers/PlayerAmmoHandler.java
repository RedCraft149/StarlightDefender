package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.GPolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.GSyncPacketReceiver;
import com.redcraft.communication.packets.handlers.Receiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.events.QuickEvent;

public class PlayerAmmoHandler extends GPolySyncPacketReceiver {

    public PlayerAmmoHandler() {
        super("player_ammo");
        setTarget(this);
    }

    @Receiver(fields = {"ammo"})
    public void handle(int ammo) {
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class)
                .throwEvent(new QuickEvent("ammo_changed").set("ammo",ammo));
        CUniverse universe = Shared.CLIENT.get(CComponents.universe, CUniverse.class);
        universe.getPlayer().setAmmo(ammo);
    }
}
