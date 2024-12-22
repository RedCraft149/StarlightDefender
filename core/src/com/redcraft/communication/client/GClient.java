package com.redcraft.communication.client;

import com.redcraft.communication.connections.ClientConnection;
import com.redcraft.communication.connections.LocalClientConnection;
import com.redcraft.communication.connections.LocalConnection;
import com.redcraft.communication.connections.SocketClientConnection;
import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.packets.handlers.DistributingPacketHandler;
import com.redcraft.communication.packets.handlers.PacketHandler;
import com.redcraft.communication.sockets.NetSocket;
import com.redcraft.rlib.Processable;

import java.util.Collection;

/**
 * A general use-case client.
 */
public class GClient implements Processable {
    ClientConnection clientConnection;
    DistributingPacketHandler handler;
    Thread thread;
    PacketList packetList;

    public GClient(ClientConnection clientConnection, PacketList packetList) {
        this.clientConnection = clientConnection;
        this.clientConnection.setHandler(this::handle);
        this.packetList = packetList;

        handler = new DistributingPacketHandler();
        handler.setDefaultPacketHandler(PacketHandler.of(packet -> System.err.println("[CLIENT] Unknown packet received: "+packet.getClass()+".")));
    }

    public void addPacketHandler(PacketHandler<?> handler) {
        this.handler.addPacketHandler(handler);
    }

    public void addPacketHandlers(Collection<PacketHandler<?>> handlers) {
        this.handler.addPacketHandlers(handlers);
    }
    public DistributingPacketHandler getRootHandler() {
        return handler;
    }
    public PacketList getPacketList() {
        return packetList;
    }

    public void start() {
        handleOnNewThread();
    }
    public void handleOnNewThread() {
        thread = new Thread(this::handle);
        thread.start();
    }
    public void handle() {
        while (isRunning()) {
            Packet packet = clientConnection.read();
            if(packet==null) continue;
            handler.receive(packet);
        }
    }

    @Override
    public void process(float dt) {
        handler.process(dt);
    }
    public boolean send(Packet packet) {
        return clientConnection.send(packet);
    }

    public boolean isRunning() {
        return clientConnection.isRunning();
    }
    public void stop() {
        clientConnection.stop();
        if(thread!=null) thread.interrupt();
    }

    /**
     * Create a new GClient with network communication.
     * @param packetList List of packets to use
     * @param host The target host
     * @param port The target IP
     * @return A new GClient
     */
    public static GClient net(PacketList packetList, String host, int port) {
        ClientConnection connection = new SocketClientConnection(packetList,null,new NetSocket(host,port,null));
        return new GClient(connection,packetList);
    }
    public static GClient local() {
        LocalClientConnection connection = new LocalClientConnection(null);
        LocalConnection.connect(connection.channel);
        return new GClient(connection,null);
    }
    public static GClient local(PacketList list) {
        LocalClientConnection connection = new LocalClientConnection(null);
        LocalConnection.connect(connection.channel);
        return new GClient(connection,list);
    }
}
