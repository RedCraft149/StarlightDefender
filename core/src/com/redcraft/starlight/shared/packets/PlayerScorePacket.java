package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.TypeConversions;

public class PlayerScorePacket implements Packet {

    int score;

    public PlayerScorePacket(int score) {
        this.score = score;
    }

    public PlayerScorePacket() {
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.PLAYER_SCORE;
    }

    @Override
    public byte[] pack() {
        return TypeConversions.intToBytes(score);
    }

    @Override
    public void unpack(byte[] bytes) {
        score = TypeConversions.bytesToInt(bytes);
    }

    @Override
    public Packet copy() {
        return new PlayerScorePacket(score);
    }

    public int score() {
        return score;
    }
}
