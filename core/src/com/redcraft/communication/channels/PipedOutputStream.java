package com.redcraft.communication.channels;

import java.io.IOException;
import java.io.OutputStream;

public class PipedOutputStream extends OutputStream {

    byte[] buffer;
    int capacity;
    int position;

    public PipedOutputStream(int capacity) {
        this.capacity = capacity;
        this.position = 0;
        this.buffer = new byte[capacity];
    }

    public byte getBuffer(int position) {
        return buffer[position];
    }

    @Override
    public void write(int b) throws IOException {
        buffer[position] = (byte) b;
        step();
    }

    public void step() {
        position++;
        if(position>=capacity) position = 0;
    }

    public int step(int position) {
        position++;
        if(position>=capacity) position = 0;
        return position;
    }
}
