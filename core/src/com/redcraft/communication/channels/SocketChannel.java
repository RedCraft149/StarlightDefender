package com.redcraft.communication.channels;


import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.sockets.Socket;

/**
 * Represents an IndirectChannel from a socket to another socket.
 */
public class SocketChannel extends IndirectChannel{
    Socket socket;

    public SocketChannel(int maxPacketSize, Socket socket) {
        super(maxPacketSize);
        this.socket = socket;
    }
    public SocketChannel(int maxPacketSize, PacketList packetList, Socket socket) {
        super(maxPacketSize,packetList);
        this.socket = socket;
    }

    public void close() {
        socket.dispose();
    }

    public boolean isOpen() {
        return socket.isConnected() && super.isOpen();
    }
}
