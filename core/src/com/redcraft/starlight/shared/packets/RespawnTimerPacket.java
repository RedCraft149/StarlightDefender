package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

public class RespawnTimerPacket implements Packet {

    float timer;

    public RespawnTimerPacket(float timer) {
        this.timer = timer;
    }

    public RespawnTimerPacket() {
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.RESPAWN_TIMER;
    }

    @Override
    public byte[] pack() {
        return new ByteStringBuilder().begin(4,false).append(timer).end();
    }

    @Override
    public void unpack(byte[] bytes) {
        timer = new ByteStringReader().use(bytes).readFloat();
    }

    @Override
    public Packet copy() {
        return new RespawnTimerPacket(timer);
    }

    public float time() {
        return timer;
    }
}
