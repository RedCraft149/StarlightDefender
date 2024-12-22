package com.redcraft.communication.connections;

import com.redcraft.communication.channels.Channel;

import java.util.ArrayList;
import java.util.List;

public class LocalConnection {

    private static final List<Channel> incomingConnections = new ArrayList<>();

    public static void connect(Channel channel) {
        synchronized (incomingConnections) {
            incomingConnections.add(channel);
        }
    }

    public static Channel accept() {
        synchronized (incomingConnections) {
            if(incomingConnections.isEmpty()) return null;
            return incomingConnections.remove(0);
        }
    }

    public static boolean hasIncomingConnection() {
        synchronized (incomingConnections) {
            return !incomingConnections.isEmpty();
        }
    }
}
