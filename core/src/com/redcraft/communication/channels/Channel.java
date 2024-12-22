package com.redcraft.communication.channels;

import com.redcraft.communication.exceptions.CommunicationException;
import com.redcraft.communication.packets.Packet;

/**
 * A Channel is used to send data between a client and server.
 */
public interface Channel {
    /**
     * Send a packet to a connected channel.
     * @param packet Packet to send.
     */
    void send(Packet packet) throws CommunicationException;

    /**
     * Read a received packet.
     * @return A received packet or null.
     */
    Packet read() throws CommunicationException;

    /**
     * @return true if the channel is still opened and therefore can receive and send data.
     */
    boolean isOpen();

    /**
     * Close this channel.
     * @throws CommunicationException
     */
    void close() throws CommunicationException;
}
