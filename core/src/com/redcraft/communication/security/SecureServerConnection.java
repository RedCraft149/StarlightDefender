package com.redcraft.communication.security;

import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.server.GClientHandler;

import java.security.PublicKey;

public class SecureServerConnection {
    PublicKey localPublicKey;
    Security security;
    GClientHandler handler;
    SecureReceiver receiver;

    public SecureServerConnection(GClientHandler handler) {
        this.handler = handler;
        this.security = new Security();
        this.security.setToKeyPair();
        this.localPublicKey = security.getPublicKey();

        this.receiver = new SecureReceiver(security,handler.getRootHandler(),handler.packetList());
        this.handler.addPacketHandler(receiver);
    }

    public void sendLocalPublicKey() {
        handler.send(new PublicKeyPacket(localPublicKey));
    }
    public void sendEncryptedPacket(Packet packet) {
        handler.send(EncryptedPacket.encrypt(packet,security));
    }
    public void sendEncryptedPacketExcluding(Packet packet) {
        handler.sendToOthers(EncryptedPacket.encrypt(packet,security));
    }
    public void sendEncryptedPacketToAll(Packet packet) {
        handler.sendToAll(EncryptedPacket.encrypt(packet,security));
    }
}
