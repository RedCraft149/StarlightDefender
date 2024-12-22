package com.redcraft.communication.connections;

import com.redcraft.communication.channels.Channel;
import com.redcraft.communication.channels.DirectChannel;
import com.redcraft.rlib.function.Consumer;

public class LocalServerConnection implements ServerConnection{

    Consumer<Channel> handler;
    boolean running;
    long acceptPeriod;

    public LocalServerConnection(long acceptPeriod, Consumer<Channel> handler) {
        this.acceptPeriod = acceptPeriod;
        this.handler = handler;
        running = true;
    }

    public LocalServerConnection(Consumer<Channel> handler) {
        this(1000,handler);
    }

    @Override
    public void setHandler(Consumer<Channel> handler) {
        this.handler = handler;
    }

    @Override
    public void listen() {
        long t0 = 0;
        while (running) {
            if(System.currentTimeMillis()-t0 < acceptPeriod) continue;
            t0 = System.currentTimeMillis();

            if(!LocalConnection.hasIncomingConnection()) continue;
            Channel channel = accept();
            if(channel==null) continue;
            handle(channel);
        }
    }

    @Override
    public Channel accept() {
        Channel channel = LocalConnection.accept();
        if(channel==null) return null;
        Channel serverside = new DirectChannel();
        DirectChannel.connect(channel,serverside);
        return serverside;
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
    }
}
