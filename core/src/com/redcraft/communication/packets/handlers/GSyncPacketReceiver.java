package com.redcraft.communication.packets.handlers;

import com.redcraft.communication.packets.GPacket;
import com.redcraft.rlib.Processable;

public class GSyncPacketReceiver extends GPacketReceiver implements Processable {

    private GPacket recent;

    public GSyncPacketReceiver(int header, Object target) {
        super(header,target);
    }
    public GSyncPacketReceiver(String header, Object target) {
        this(header.hashCode(),target);
    }

    public GSyncPacketReceiver(int header) {
        super(header);
    }
    public GSyncPacketReceiver(String header) {
        this(header.hashCode());
    }

    @Override
    public void receive(GPacket packet) {
        recent = packet;
    }

    @Override
    public void process(float dt) {
        if(recent != null) {
            reflector.reflect(recent);
            recent = null;
        }
    }
}
