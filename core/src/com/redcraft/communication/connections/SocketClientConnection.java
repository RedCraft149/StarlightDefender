package com.redcraft.communication.connections;

import com.redcraft.communication.channels.SocketChannel;
import com.redcraft.communication.exceptions.CommunicationException;
import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.sockets.Socket;

public class SocketClientConnection implements ClientConnection {

    Socket socket;
    SocketChannel channel;
    Runnable handler;
    boolean running;

    public SocketClientConnection(PacketList packetList, Runnable handler, Socket socket) {
        this.socket = socket;
        this.channel = new SocketChannel(65536,packetList,socket);
        this.channel.setInput(socket.getInputStream());
        this.channel.setDestination(socket.getOutputStream());
        this.handler = handler;
        running = true;
    }

    @Override
    public void handle() {
        handler.run();
    }

    @Override
    public void setHandler(Runnable r) {
        this.handler = r;
    }

    @Override
    public Packet read() {
        if(!running) return null;
        try {
            return channel.read();
        } catch (CommunicationException e) {
            running = false;
            System.err.println("Communication exception in client, stopping.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean send(Packet packet) {
        if(!running) return false;
        try {
            channel.send(packet);
            return true;
        } catch (CommunicationException e) {
            running = false;
            System.err.println("Communication exception in client, stopping.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void stop() {
        running = false;
        channel.close();
    }
}
