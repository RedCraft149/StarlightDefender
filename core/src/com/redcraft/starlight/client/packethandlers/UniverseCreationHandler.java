package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.packets.UniverseCreationPacket;

public class UniverseCreationHandler extends PolySyncPacketReceiver<UniverseCreationPacket> {

    public UniverseCreationHandler() {
        super(StarlightDefenderPacketList.UNIVERSE_CREATION,null);
    }

    @Override
    public void handle(UniverseCreationPacket universeCreationPacket) {
        CUniverse universe = new CUniverse(universeCreationPacket.width,universeCreationPacket.height,universeCreationPacket.playerUUID);
        Shared.CLIENT.run(CComponents.createUniverse,universe);
    }
}
