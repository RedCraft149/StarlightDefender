package com.redcraft.communication.channels;

import com.redcraft.communication.exceptions.ChannelNotConnectedException;
import com.redcraft.communication.exceptions.CommunicationException;
import com.redcraft.communication.packets.Packet;

import java.util.LinkedList;
import java.util.List;

/**
 * A direct channel connects a client with a local server on the same Java VM.
 */
public class DirectChannel implements Channel{

    DirectChannel destination;
    boolean hasDestination;
    final List<Packet> received;
    private boolean open;

    public DirectChannel() {
        received = new LinkedList<>();
        destination = null;
        hasDestination = false;
        open = true;
    }

    public void setDestination(DirectChannel destination) {
        this.destination = destination;
        hasDestination = this.destination != null;
    }
    public DirectChannel getDestination() throws CommunicationException {
        if(!hasDestination) throw new ChannelNotConnectedException();
        return destination;
    }
    public boolean hasDestination() {
        return hasDestination;
    }

    public void accept(Packet packet) {
        synchronized (received) {
            received.add(packet);
        }
    }

    @Override
    public void send(Packet packet) throws CommunicationException {
        if(!hasDestination) throw new ChannelNotConnectedException();
        destination.accept(packet.copy());
    }

    @Override
    public Packet read() {
        Packet packet;
        synchronized (received) {
            if (received.isEmpty()) return null;
            packet = received.get(0);
            received.remove(0);
        }
        return packet;
    }

    @Override
    public boolean isOpen() {
        return open;
    }
    @Override
    public void close() {
        open = false;
        destination = null;
        hasDestination = false;
    }

    public static void connect(Channel c0, Channel c1) {
        if(!(c0 instanceof DirectChannel) || !(c1 instanceof DirectChannel)) throw new IllegalArgumentException("Channels have to be of type DirectChannel!");
        DirectChannel d0 = (DirectChannel) c0;
        DirectChannel d1 = (DirectChannel) c1;
        d0.setDestination(d1);
        d1.setDestination(d0);
    }
}
