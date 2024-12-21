package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;

public class HeartbeatPacket implements Packet {
    @Override
    public int header() {
        return "internal:heartbeat".hashCode();
    }

    @Override
    public byte[] pack() {
        return new byte[0];
    }

    @Override
    public void unpack(byte[] bytes) {
    }

    @Override
    public Packet copy() {
        return new HeartbeatPacket();
    }
}
