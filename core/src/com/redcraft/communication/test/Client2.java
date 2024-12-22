package com.redcraft.communication.test;

import com.redcraft.communication.client.GClient;
import com.redcraft.communication.packets.GPacket;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.packets.handlers.GPacketAction;
import com.redcraft.communication.packets.handlers.GPacketReceiver;
import com.redcraft.communication.security.EncryptedPacket;
import com.redcraft.communication.security.PublicKeyPacket;
import com.redcraft.communication.security.SecureClientConnection;

import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        PacketList packetList = new PacketList();
        packetList.addPacket(GPacket.parse("name:message, str:text"));
        packetList.addPacket(PublicKeyPacket::new);
        packetList.addPacket(EncryptedPacket::new);
        GClient client = GClient.net(packetList,"localhost",14900);

        client.addPacketHandler(GPacketReceiver.instant("message", GPacketAction.create((String text)-> System.out.println(text),"text")));
        SecureClientConnection secureClientConnection = new SecureClientConnection(client);
        secureClientConnection.sendLocalPublicKey();

        client.start();

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            secureClientConnection.sendEncryptedPacket(packetList.newGPacket("message").set("text", line));
        }
    }
}
