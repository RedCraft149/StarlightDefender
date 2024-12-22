package com.redcraft.communication.server;

import com.redcraft.communication.channels.Channel;
import com.redcraft.communication.exceptions.CommunicationException;
import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.packets.handlers.PacketHandler;

import java.util.UUID;

public abstract class ClientHandler {
    private Channel channel;
    private PacketHandler<Packet> handler;
    private Thread thread;
    protected UUID uuid;
    protected boolean running;

    public ClientHandler() {
        running = true;
        handler = null;
    }
    public void send(Packet packet) {
        if(!running) return;
        try {
            channel.send(packet);
        } catch (CommunicationException ex) {
            running = false;
            System.err.println("Communication exception while sending packet.");
        }
    }

    public boolean handleOnNewThread() {
        if(this.thread!=null) return false;
        thread = new Thread(this::handle);
        thread.start();
        return true;
    }
    public void handle() {
        init();

        while (running) {
            try {
                Packet packet = channel.read();
                if (packet == null) continue;
                handler.receive(packet);
            } catch (CommunicationException ex) {
                running = false;
                System.err.println("Communication exception while handling client, stopping.");
            }
        }

        try {
            end();
            channel.close();
        } catch (CommunicationException ex) {
            System.err.println("Communication exception while closing client.");
        }
    }

    public Packet read() {
        if(!running) return null;
        try {
            return channel.read();
        } catch (CommunicationException e) {
            running = false;
            return null;
        }
    }

    public void stop() {
        running = false;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    public void setHandler(PacketHandler<Packet> handler) {
        this.handler = handler;
    }

    public abstract void init();
    public abstract void end();
}
