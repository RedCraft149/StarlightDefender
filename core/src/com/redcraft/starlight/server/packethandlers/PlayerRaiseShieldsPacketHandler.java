package com.redcraft.starlight.server.packethandlers;

import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.server.SConstants;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.communication.packets.GPacket;

import java.util.UUID;

public class PlayerRaiseShieldsPacketHandler extends SyncPacketReceiver<GPacket> {

    UUID uuid;
    Universe universe;

    public PlayerRaiseShieldsPacketHandler(UUID uuid) {
        super("player_raise_shields",null);
        this.uuid = uuid;
        universe = Shared.SERVER.get(SConstants.universe, Universe.class);
    }

    @Override
    public void handle(GPacket packet) {
        Entity e = universe.entityFinder().searchEntity(uuid);
        if(e==null) return;
        if(!(e instanceof SPlayer)) return;
        SPlayer player = (SPlayer) e;
        if(player.shieldsUpTime <= 0f) return;
        player.raiseShields();
    }
}
