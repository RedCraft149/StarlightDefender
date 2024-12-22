package com.redcraft.communication.sockets;

public interface ServerSocket {
    Socket accept(SocketHints hints);
    void dispose();
}
