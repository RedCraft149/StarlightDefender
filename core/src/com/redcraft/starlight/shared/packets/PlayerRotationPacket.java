package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.TypeConversions;


public class PlayerRotationPacket implements Packet {

    float rotation;

    public PlayerRotationPacket(float rotation) {
        this.rotation = rotation;
    }

    public PlayerRotationPacket() {
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.PLAYER_ROTATION;
    }

    @Override
    public byte[] pack() {
        return TypeConversions.floatToBytes(rotation);
    }

    @Override
    public void unpack(byte[] bytes) {
        rotation = TypeConversions.bytesToFloat(bytes);
    }

    @Override
    public Packet copy() {
        return new PlayerRotationPacket(rotation);
    }

    public float rotation() {
        return rotation;
    }
}
