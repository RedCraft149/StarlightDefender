package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.GPolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.GSyncPacketReceiver;
import com.redcraft.communication.packets.handlers.Receiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.shared.Shared;

public class ShieldHitHandler extends GPolySyncPacketReceiver {
    public ShieldHitHandler() {
        super("player_shield_hit");
        setTarget(this);
    }

    @Receiver(fields = {"x","y","time"})
    public void handle(float x, float y, float time) {
        CUniverse universe = Shared.CLIENT.get(CComponents.universe, CUniverse.class);
        universe.getPlayer().addShieldHit(x,y,time);
    }
}
