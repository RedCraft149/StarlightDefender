package com.redcraft.communication.server;

import com.redcraft.communication.packets.GPacket;
import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.packets.handlers.DistributingPacketHandler;
import com.redcraft.communication.packets.handlers.PacketHandler;

import java.util.Collection;

public abstract class GClientHandler extends ClientHandler {
    protected DistributingPacketHandler handler;
    protected GServer<?> server;

    public GClientHandler(GServer<?> server) {
        this.server = server;
        handler = new DistributingPacketHandler();
        handler.setDefaultPacketHandler(PacketHandler.of(packet -> System.err.println("[SERVER] Unknown packet received: "+packet.getClass()+".")));
        super.setHandler(handler);
    }

    public void addPacketHandler(PacketHandler<?> handler) {
        this.handler.addPacketHandler(handler);
    }
    public void addPacketHandlers(Collection<PacketHandler<?>> handlers) {
        this.handler.addPacketHandlers(handlers);
    }

    @Deprecated
    public void setHandler(PacketHandler<Packet> v) {
        throw new UnsupportedOperationException();
    }
    public void process(float dt) {
        handler.process(dt);
    }

    public void sendToOthers(Packet packet) {
        server.sendExcluding(packet,uuid);
    }
    public void sendToAll(Packet packet) {
        server.sendToAll(packet);
    }

    public PacketList packetList() {
        return server.availablePackets();
    }

    public <T> T newPacket(String header, Class<T> type) {
        Packet packet = packetList().newPacket(header.hashCode());
        if(type.isInstance(packet)) return type.cast(packet);
        else return null;
    }

    public GPacket newGPacket(String header) {
        return packetList().newGPacket(header);
    }
    public DistributingPacketHandler getRootHandler() {
        return handler;
    }

}
