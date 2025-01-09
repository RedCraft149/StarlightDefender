package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

public class PlayerPausePacket implements Packet {

    String reason;
    boolean resume;
    boolean levelCleared;

    public PlayerPausePacket(String reason, boolean resume, boolean levelCleared) {
        this.reason = reason;
        this.resume = resume;
        this.levelCleared = levelCleared;
    }

    public PlayerPausePacket() {
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
        builder.append(resume);
        builder.append(levelCleared);
        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader().use(bytes);
        int length = reader.readInteger();
        byte[] data = reader.readByteArray(length);
        reason = new String(data);
        resume = reader.readBoolean();
        levelCleared = reader.readBoolean();
    }

    @Override
    public Packet copy() {
        return new PlayerPausePacket(reason,resume,levelCleared);
    }

    public String reason() {
        return reason;
    }
    public boolean resume() {
        return resume;
    }
    public boolean levelCleared() {
        return levelCleared;
    }
}
