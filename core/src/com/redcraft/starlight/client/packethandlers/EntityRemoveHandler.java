package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CPlayer;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.EntityRemovePacket;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.events.QuickEvent;

public class EntityRemoveHandler extends PolySyncPacketReceiver<EntityRemovePacket> {

    public EntityRemoveHandler() {
        super(StarlightDefenderPacketList.ENTITY_REMOVE,null);
    }

    @Override
    public void handle(EntityRemovePacket packet) {
        Universe universe = Shared.CLIENT.get(CComponents.universe, Universe.class);
        Entity e = universe.entityFinder().searchEntity(packet.uuid,packet.x,packet.y);
        System.out.println("remove:"+packet.uuid);
        if(e == null) {
            //later removal, to remove entities added and removed during respawn phase
            System.out.println("later");
            universe.entityDistributor().removeEntity(()->universe.entityFinder().searchEntity(packet.uuid, packet.x, packet.y),5);
        } else {
            System.out.println("now");
            e.remove(); //instant removal
        }

        if(e instanceof CPlayer) {
            Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class)
                    .throwEvent(new QuickEvent("player_leave").set("uuid", packet.uuid));
        }
    }
}
