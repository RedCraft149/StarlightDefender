package com.redcraft.communication.connections;

import com.redcraft.communication.channels.Channel;
import com.redcraft.communication.channels.SocketChannel;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.sockets.ServerSocket;
import com.redcraft.communication.sockets.Socket;
import com.redcraft.communication.sockets.SocketHints;
import com.redcraft.rlib.function.Consumer;

public class SocketServerConnection implements ServerConnection {

    ServerSocket serverSocket;
    PacketList packetList;
    Consumer<Channel> handler;
    boolean running;

    public SocketServerConnection(PacketList list, Consumer<Channel> handler, ServerSocket socket) {
        this.packetList = list;
        this.handler = handler;
        this.serverSocket = socket;
        running = true;
    }

    @Override
    public Channel accept() {
        Socket socket = serverSocket.accept(new SocketHints());
        return createChannel(socket);
    }

    private Channel createChannel(Socket socket) {
        SocketChannel channel = new SocketChannel(65536,packetList,socket);
        channel.setInput(socket.getInputStream());
        channel.setDestination(socket.getOutputStream());
        return channel;
    }

    @Override
    public void handle(Channel channel) {
        handler.accept(channel);
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void stop() {
        running = false;
        serverSocket.dispose();
    }

    @Override
    public void setHandler(Consumer<Channel> handler) {
        this.handler = handler;
    }
}
