package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

import java.util.UUID;

public class UniverseCreationPacket implements Packet {

    public int width;
    public int height;
    public UUID playerUUID;

    public UniverseCreationPacket(int width, int height, UUID playerUUID) {
        this.width = width;
        this.height = height;
        this.playerUUID = playerUUID;
    }

    public UniverseCreationPacket() {
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.UNIVERSE_CREATION;
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder byteStringBuilder = new ByteStringBuilder();
        byteStringBuilder.begin(0,true);
        byteStringBuilder.append(width);
        byteStringBuilder.append(height);
        byteStringBuilder.append(playerUUID.getMostSignificantBits())
                         .append(playerUUID.getLeastSignificantBits());
        return byteStringBuilder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader();
        reader.use(bytes);
        width = reader.readInteger();
        height = reader.readInteger();
        playerUUID = new UUID(reader.readLong(),reader.readLong());
    }

    @Override
    public Packet copy() {
        return new UniverseCreationPacket(width, height, new UUID(playerUUID.getMostSignificantBits(),playerUUID.getLeastSignificantBits()));
    }
}
