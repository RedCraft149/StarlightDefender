package com.redcraft.communication.connections;

import com.redcraft.communication.channels.DirectChannel;
import com.redcraft.communication.exceptions.CommunicationException;
import com.redcraft.communication.packets.Packet;

public class LocalClientConnection implements ClientConnection {

    Runnable handler;
    public DirectChannel channel;
    boolean running;

    public LocalClientConnection(Runnable handler) {
        this.handler = handler;
        channel = new DirectChannel();
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
        return channel.read();
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
