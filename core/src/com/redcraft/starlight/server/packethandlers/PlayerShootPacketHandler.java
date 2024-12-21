package com.redcraft.starlight.server.packethandlers;

import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.server.SConstants;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.communication.packets.GPacket;

import java.util.UUID;

public class PlayerShootPacketHandler extends SyncPacketReceiver<GPacket> {

    Universe universe;
    UUID uuid;

    public PlayerShootPacketHandler(UUID uuid) {
        super("player_shoot",null);
        universe = Shared.SERVER.get(SConstants.universe, Universe.class);
        this.uuid = uuid;
    }

    @Override
    public void handle(GPacket bulletShootPacket) {
        Entity e = universe.entityFinder().searchEntity(uuid);
        if(e == null) return;
        if(!(e instanceof SPlayer)) return;
        SPlayer player = (SPlayer) e;

        player.shoot();

    }
}
