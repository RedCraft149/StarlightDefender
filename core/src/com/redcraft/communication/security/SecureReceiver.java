package com.redcraft.communication.security;

import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.packets.handlers.PacketHandler;

public class SecureReceiver implements PacketHandler<Packet> {

    Security security;
    PacketHandler<Packet> handler;
    PacketList list;

    public SecureReceiver(Security security, PacketHandler<Packet> handler, PacketList list) {
        this.security = security;
        this.handler = handler;
        this.list = list;
    }

    @Override
    public void receive(Packet packet) {
        if(packet instanceof PublicKeyPacket) setPublicKey((PublicKeyPacket) packet);
        if(packet instanceof EncryptedPacket) receiveEncryptedPacket((EncryptedPacket) packet);
    }

    private void setPublicKey(PublicKeyPacket packet) {
        System.out.println("received public key");
        security.setEncryptionKey(packet.key);
    }
    private void receiveEncryptedPacket(EncryptedPacket packet) {
        handler.receive(packet.decrypt(security,list));
    }



    @Override
    public int header() {
        return IGNORE_HEADER;
    }
}
