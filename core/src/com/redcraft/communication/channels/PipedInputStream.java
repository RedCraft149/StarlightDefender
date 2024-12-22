package com.redcraft.communication.channels;

import java.io.IOException;
import java.io.InputStream;

public class PipedInputStream extends InputStream {

    PipedOutputStream stream;
    int position;
    boolean isConnected;

    public PipedInputStream() {
        stream = null;
        position = 0;
        isConnected = false;
    }

    public void setStream(PipedOutputStream stream) {
        this.stream = stream;
        this.isConnected = this.stream != null;
    }

    @Override
    public int read() throws IOException {
        if(!isConnected) throw new IOException();
        byte b = stream.getBuffer(position);
        position = stream.step(position);
        return b & 0xFF;
    }
}
