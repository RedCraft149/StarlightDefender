package com.redcraft.communication.connections;

import com.redcraft.communication.channels.Channel;
import com.redcraft.communication.exceptions.CommunicationException;
import com.redcraft.rlib.function.Consumer;

public interface ServerConnection {
    default void listen() {
        try {
            while (isRunning()) {
                Channel c = accept();
                handle(c);
            }
        } catch (Exception e) {
            System.out.println("Listening interrupted: "+e);
        }
    }
    default Thread listenOnNewThread() {
        Thread t = new Thread(this::listen);
        t.start();
        return t;
    }


    void setHandler(Consumer<Channel> handler);

    Channel accept();
    void handle(Channel channel);
    default void disconnect(Channel channel) {
        try {
            channel.close();
        } catch (CommunicationException e) {
            System.err.println("Could not disconnect channel.");
        }
    }

    boolean isRunning();
    void stop();
}
