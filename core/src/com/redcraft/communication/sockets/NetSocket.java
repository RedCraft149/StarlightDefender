package com.redcraft.communication.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class NetSocket implements Socket{

    java.net.Socket socket;

    public NetSocket(String host, int port, SocketHints hints) {
        try {
            socket = new java.net.Socket();
            applyHints(hints);
            InetSocketAddress address = new InetSocketAddress(host,port);
            if(hints!=null) {
                socket.connect(address,hints.connectTimeout);
            } else {
                socket.connect(address);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public NetSocket(java.net.Socket socket, SocketHints hints) {
        this.socket = socket;
        applyHints(hints);
    }

    private void applyHints (SocketHints hints) {
        if (hints != null) {
            try {
                socket.setPerformancePreferences(hints.performancePrefConnectionTime, hints.performancePrefLatency,
                    hints.performancePrefBandwidth);
                socket.setTrafficClass(hints.trafficClass);
                socket.setTcpNoDelay(hints.tcpNoDelay);
                socket.setKeepAlive(hints.keepAlive);
                socket.setSendBufferSize(hints.sendBufferSize);
                socket.setReceiveBufferSize(hints.receiveBufferSize);
                socket.setSoLinger(hints.linger, hints.lingerDuration);
                socket.setSoTimeout(hints.socketTimeout);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isConnected() {
        if(socket==null) return false;
        return socket.isConnected();
    }

    @Override
    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getRemoteAddress() {
        return socket.getRemoteSocketAddress().toString();
    }

    @Override
    public void dispose() {
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            System.err.println("Socket cannot be closed: "+e);
        }
    }
}
