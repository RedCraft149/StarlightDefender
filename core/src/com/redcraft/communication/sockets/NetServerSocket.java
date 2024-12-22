package com.redcraft.communication.sockets;

import java.net.InetSocketAddress;

public class NetServerSocket implements ServerSocket{

    private java.net.ServerSocket server;

    public NetServerSocket( int port, ServerSocketHints hints) {
        this(null, port, hints);
    }

    public NetServerSocket ( String hostname, int port, ServerSocketHints hints) {
        // create the server socket
        try {
            // initialize
            server = new java.net.ServerSocket();
            if (hints != null) {
                server.setPerformancePreferences(hints.performancePrefConnectionTime, hints.performancePrefLatency,
                    hints.performancePrefBandwidth);
                server.setReuseAddress(hints.reuseAddress);
                server.setSoTimeout(hints.acceptTimeout);
                server.setReceiveBufferSize(hints.receiveBufferSize);
            }

            // and bind the server...
            InetSocketAddress address;
            if (hostname != null) {
                address = new InetSocketAddress(hostname, port);
            } else {
                address = new InetSocketAddress(port);
            }

            if (hints != null) {
                server.bind(address, hints.backlog);
            } else {
                server.bind(address);
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot create a server socket at port " + port + ".", e);
        }
    }

    @Override
    public Socket accept(SocketHints hints) {
        try {
            return new NetSocket(server.accept(), hints);
        } catch (Exception e) {
            throw new RuntimeException("Error accepting socket.", e);
        }
    }

    @Override
    public void dispose () {
        if (server != null) {
            try {
                server.close();
                server = null;
            } catch (Exception e) {
                throw new RuntimeException("Error closing server.", e);
            }
        }
    }
}
