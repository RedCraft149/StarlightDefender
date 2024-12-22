package com.redcraft.communication.security;

import com.redcraft.communication.client.GClient;
import com.redcraft.communication.packets.Packet;

import java.security.PublicKey;

public class SecureClientConnection {
    PublicKey localPublicKey;
    Security security;
    GClient client;
    SecureReceiver receiver;

    public SecureClientConnection(GClient client) {
        this.client = client;
        this.security = new Security();
        this.security.setToKeyPair();
        this.localPublicKey = security.getPublicKey();

        this.receiver = new SecureReceiver(security,client.getRootHandler(),client.getPacketList());
        this.client.addPacketHandler(receiver);
    }

    public void sendLocalPublicKey() {
        client.send(new PublicKeyPacket(localPublicKey));
    }
    public void sendEncryptedPacket(Packet packet) {
        client.send(EncryptedPacket.encrypt(packet,security));
    }

}
