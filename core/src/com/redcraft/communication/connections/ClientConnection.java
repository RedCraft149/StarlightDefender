package com.redcraft.communication.connections;

import com.redcraft.communication.packets.Packet;

public interface ClientConnection {
    void handle();
    default Thread handleOnNewThread() {
        Thread t = new Thread(this::handle);
        t.start();
        return t;
    }


    void setHandler(Runnable r);

    Packet read();
    boolean send(Packet packet);

    boolean isRunning();
    void stop();
}
