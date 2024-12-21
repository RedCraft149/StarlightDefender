package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

public class PlayerDeathPacket implements Packet {

    String reason;
    boolean respawn;

    public PlayerDeathPacket(String reason, boolean respawn) {
        this.reason = reason;
        this.respawn = respawn;
    }

    public PlayerDeathPacket() {
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.PLAYER_DEATH;
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder builder = new ByteStringBuilder().begin(0,true);
        byte[] b = reason.getBytes();
        builder.append(b.length);
        builder.append(b);
        builder.append(respawn);
        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader().use(bytes);
        int length = reader.readInteger();
        byte[] data = reader.readByteArray(length);
        reason = new String(data);
        respawn = reader.readBoolean();
    }

    @Override
    public Packet copy() {
        return new PlayerDeathPacket(reason,respawn);
    }

    public String reason() {
        return reason;
    }
    public boolean respawn() {
        return respawn;
    }
}
