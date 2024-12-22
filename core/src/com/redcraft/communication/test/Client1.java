package com.redcraft.communication.test;

import com.redcraft.communication.client.GClient;
import com.redcraft.communication.fields.GFieldPacket;
import com.redcraft.communication.fields.GFieldRegistry;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.security.EncryptedPacket;
import com.redcraft.communication.security.PublicKeyPacket;
import com.redcraft.communication.security.SecureClientConnection;
import com.redcraft.rlib.events.EventHandler;

import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        PacketList packetList = new PacketList();
        GFieldRegistry registry = new GFieldRegistry();
        EventHandler eventHandler = new EventHandler();

        packetList.addPacket(GFieldPacket::new);
        packetList.addPacket(PublicKeyPacket::new);
        packetList.addPacket(EncryptedPacket::new);
        GClient client = GClient.net(packetList,"localhost",14900);

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
