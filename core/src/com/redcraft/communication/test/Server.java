package com.redcraft.communication.test;

import com.redcraft.communication.packets.GPacket;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.packets.handlers.GPacketAction;
import com.redcraft.communication.packets.handlers.GPacketReceiver;
import com.redcraft.communication.security.EncryptedPacket;
import com.redcraft.communication.security.PublicKeyPacket;
import com.redcraft.communication.security.SecureServerConnection;
import com.redcraft.communication.server.GClientHandler;
import com.redcraft.communication.server.GServer;

public class Server {
    public static void main(String[] args) {

        PacketList packetList = new PacketList();
        packetList.addPacket(GPacket.parse("name:message, str:text"));
        packetList.addPacket(PublicKeyPacket::new);
        packetList.addPacket(EncryptedPacket::new);
        System.out.println("message:id="+"message".hashCode());

        GServer<MyClientHandler> server = GServer.net(packetList, MyClientHandler::new,14900);
        server.start();
    }

    private static class MyClientHandler extends GClientHandler {
        SecureServerConnection secureServerConnection;
        public MyClientHandler(GServer<MyClientHandler> server) {
            super(server);
        }

        private void messageHandler(String text) {
            System.out.println("received: "+text);
            long t0 = System.currentTimeMillis();
            sendToOthers(newGPacket("message").set("text",text));
            System.out.println("sent in "+(System.currentTimeMillis()-t0));
        }

        @Override
        public void init() {
            System.out.println("connected: "+this.uuid);
            addPacketHandler(GPacketReceiver.instant("message", GPacketAction.create(this::messageHandler,"text")));
            secureServerConnection = new SecureServerConnection(this);
            secureServerConnection.sendLocalPublicKey();
        }

        @Override
        public void end() {
            System.out.println("disconnected: "+this.uuid);
        }
    }
}
