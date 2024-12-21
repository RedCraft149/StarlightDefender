package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CPlayer;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.EntityHealthPacket;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.world.Universe;

public class EntityHealthChangeHandler extends PolySyncPacketReceiver<EntityHealthPacket> {


    public EntityHealthChangeHandler() {
        super(StarlightDefenderPacketList.ENTITY_HEALTH,null);
    }

    @Override
    public void handle(EntityHealthPacket entityHealthPacket) {
        Universe universe = Shared.CLIENT.get(CComponents.universe, Universe.class);
        Entity entity = universe.entityFinder().searchEntity(entityHealthPacket.uuid);
        if(entity==null) return;
        if(!(entity instanceof CPlayer)) return;
        CPlayer player = (CPlayer) entity;
        player.setHealth(entityHealthPacket.newHealth);
    }
}
