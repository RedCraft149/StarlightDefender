package com.redcraft.communication.packets.handlers;


import com.redcraft.communication.packets.GPacket;

public class GInstantPacketReceiver extends GPacketReceiver {


    public GInstantPacketReceiver(int header, Object target) {
        super(header,target);
    }
    public GInstantPacketReceiver(String header, Object target) {
        this(header.hashCode(),target);
    }

    public GInstantPacketReceiver(int header) {
        super(header);
    }

    public GInstantPacketReceiver(String header) {
        this(header.hashCode());
    }

    @Override
    public void receive(GPacket packet) {
        reflector.reflect(packet);
    }


}
