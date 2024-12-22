package com.redcraft.communication.packets.handlers;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.Processable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DistributingPacketHandler implements PacketHandler<Packet>, Processable {
    List<PacketHandler<?>> handlers;
    PacketHandler<Packet> defaultPacketHandler;

    public DistributingPacketHandler() {
        handlers = new ArrayList<>();
        defaultPacketHandler = PacketHandler.of(packet -> System.err.println("Unknown packet received: "+packet.getClass()+"."));
    }

    public void setDefaultPacketHandler(PacketHandler<Packet> packetHandler) {
        this.defaultPacketHandler = packetHandler;
    }
    public void addPacketHandler(PacketHandler<?> handler) {
        this.handlers.add(handler);
    }
    public void addPacketHandlers(Collection<PacketHandler<?>> handlers) {
        this.handlers.addAll(handlers);
    }

    @Override
    public void receive(Packet packet) {
        boolean handled = false;
        for(PacketHandler<?> handler : handlers) {
            handled |= handler.accept(packet); //type checking is done in PacketHandler#receiveUnsafe
        }
        if(!handled) defaultPacketHandler.receive(packet);
    }

    @Override
    public int header() {
        return IGNORE_HEADER;
    }

    @Override
    public void process(float dt) {
        for(PacketHandler<?> handler : handlers) {
            if(handler instanceof Processable) ((Processable) handler).process(dt);
        }
    }
}
