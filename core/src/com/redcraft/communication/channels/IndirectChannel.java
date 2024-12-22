package com.redcraft.communication.channels;

import com.redcraft.communication.exceptions.*;
import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.rlib.serial.TypeConversions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * An IndirectChannel connects a client and server. The communication is not restricted
 * to the same VM.
 */
public class IndirectChannel implements Channel{

    private final int maxPacketSize;
    private OutputStream destination;
    private boolean hasDestination;
    private InputStream input;
    private boolean hasInput;
    private boolean open;

    private PacketList packetList;


    /**
     * Create a new channel. Before using it, {@link IndirectChannel#setPacketList(PacketList)} must be called.
     * @param maxPacketSize The maximum size a packet can be, in bytes. The actual data sent is 8 bytes longer
     *                      than the content of a packet. The maximum size the content of a packet can be is therefore
     *                      {@code maxPacketSize}-8 [bytes].
     */
    public IndirectChannel(int maxPacketSize) {
        this.maxPacketSize = maxPacketSize;
        this.destination = null;
        this.hasDestination = false;
        this.input = null;
        this.hasInput = false;
        open = true;
        packetList = null;
    }

    /**
     * Creates a new channel. No further setup is required.
     * @param maxPacketSize The maximum size a packet can be. See {@link IndirectChannel#IndirectChannel(int)}.
     * @param packetList A list of packets that can be sent via this channel.
     */
    public IndirectChannel(int maxPacketSize, PacketList packetList) {
        this(maxPacketSize);
        setPacketList(packetList);
    }

    /**
     * Creates a new channel with an unlimited maximum packet size (actually ~2GB).
     * Before using it, {@link IndirectChannel#setPacketList(PacketList)} must be called.
     */
    public IndirectChannel() {
        this(Integer.MAX_VALUE);
    }

    public void setDestination(OutputStream destination) {
        this.destination = destination;
        hasDestination = this.destination != null;
    }
    public void setInput(InputStream stream) {
        this.input = stream;
        this.hasInput = this.input != null;
    }

    public void setPacketList(PacketList list) {
        this.packetList = list;
    }

    @Override
    public void send(Packet packet) throws CommunicationException {
        final int header = packet.header();
        if (header == -1) throw new NoSuchPacketException();
        final byte[] headerBytes = TypeConversions.intToBytes(header);

        final byte[] packed = packet.pack();
        final int length = packed.length;
        if (length + 8 > maxPacketSize) throw new PacketTooLargeException();
        final byte[] lengthBytes = TypeConversions.intToBytes(length);

        byte[] packetBytes = new byte[length + 8];
        System.arraycopy(headerBytes, 0, packetBytes, 0, 4);
        System.arraycopy(lengthBytes, 0, packetBytes, 4, 4);
        System.arraycopy(packed, 0, packetBytes, 8, length);

        try {
            write(packetBytes);
        } catch (IOException e) {
            throw new PacketWriteException();
        }
    }

    public void write(byte[] b) throws CommunicationException, IOException {
        if(!hasDestination) throw new ChannelNotConnectedException();
        destination.write(b);
        destination.flush();
    }

    private int readInt() throws IOException, CommunicationException {
        if(!hasInput) throw new ChannelNotConnectedException();
        byte[] b = new byte[4];
        for(int i = 0; i < 4; i++) {
            b[i] = (byte) input.read();
        }
        return TypeConversions.bytesToInt(b);
    }
    private Packet readPacket() throws IOException, CommunicationException {
        if(!hasInput) throw new ChannelNotConnectedException();

        byte[] headerBytes = new byte[4];
        for(int i = 0; i < 4; i++) headerBytes[i] = (byte) input.read();
        int header = TypeConversions.bytesToInt(headerBytes);
        Packet packet = packetList.newPacket(header);
        if(packet==null) throw new NoSuchPacketException();

        byte[] size = new byte[4];
        for(int i = 0; i < 4; i++) size[i] = (byte) input.read();
        int length = TypeConversions.bytesToInt(size);
        byte[] data = new byte[length];
        for(int i = 0; i < length; i++) data[i] = (byte) input.read();

        packet.unpack(data);
        return packet;
    }

    @Override
    public Packet read() throws CommunicationException {
        if(!hasInput) throw new ChannelNotConnectedException();
        try {
            if(input.available()<4) return null;
            return readPacket();
        } catch (IOException e) {
            throw new CommunicationException();
        }
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    public InputStream getInput() {
        return input;
    }
    public OutputStream getDestination() {
        return destination;
    }

    public static void connectLocal(Channel channel0, Channel channel1) {
        if(!(channel0 instanceof IndirectChannel) || !(channel1 instanceof IndirectChannel))  throw new IllegalArgumentException("Channels have to be of type IndirectChannel!");
        IndirectChannel iChannel0 = (IndirectChannel) channel0;
        IndirectChannel iChannel1 = (IndirectChannel) channel1;

        PipedInputStream in0 = new PipedInputStream();
        PipedOutputStream out0 = new PipedOutputStream(1024);
        PipedInputStream in1 = new PipedInputStream();
        PipedOutputStream out1 = new PipedOutputStream(1024);

        //Crossing streams
        in0.setStream(out1);
        in1.setStream(out0);

        iChannel0.setDestination(out0);
        iChannel0.setInput(in0);
        iChannel1.setDestination(out1);
        iChannel1.setInput(in1);
    }

    @Override
    public void close() throws CommunicationException {
        try {
            destination.close();
            input.close();
        } catch (Exception ex) {
            throw new CommunicationException();
        }

        setDestination(null);
        setInput(null);

        open = false;
    }
}
