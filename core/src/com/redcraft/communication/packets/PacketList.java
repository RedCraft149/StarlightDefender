package com.redcraft.communication.packets;

import com.redcraft.rlib.function.Supplier;

import java.util.HashMap;
import java.util.Map;

public class PacketList {
    private final Map<Integer,Supplier<Packet>> packets;

    public PacketList() {
        this.packets = new HashMap<>();
    }

    public void addPacket(Supplier<Packet> supplier) {
        packets.put(supplier.get().header(),supplier);
    }
    public void addPacket(int header, Supplier<Packet> supplier) {
        packets.put(header,supplier);
    }
    public Supplier<Packet> getPacket(int header) {
        return packets.get(header);
    }
    public Packet newPacket(int header) {
        return packets.containsKey(header) ? packets.get(header).get() : null;
    }
    public GPacket newGPacket(int header) {
        Packet packet = newPacket(header);
        if(packet instanceof GPacket) return (GPacket) packet;
        return null;
    }
    public GPacket newGPacket(String header) {
        return newGPacket(header.hashCode());
    }
}
