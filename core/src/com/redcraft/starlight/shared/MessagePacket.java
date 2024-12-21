package com.redcraft.starlight.shared;

import com.redcraft.communication.packets.Packet;

public class MessagePacket implements Packet {

    String msg;
    public MessagePacket(String msg) {
        this.msg = msg;
    }

    @Override
    public int header() {
        return 0x1001;
    }

    @Override
    public byte[] pack() {
        return msg.getBytes();
    }

    @Override
    public void unpack(byte[] bytes) {
        msg = new String(bytes);
    }

    @Override
    public Packet copy() {
        return new MessagePacket(msg);
    }

    public String toString() {
        return "MessagePacket("+msg+")";
    }
}
