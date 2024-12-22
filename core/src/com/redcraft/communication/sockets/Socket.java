package com.redcraft.communication.sockets;

import java.io.InputStream;
import java.io.OutputStream;

public interface Socket {
    public boolean isConnected();

    /** @return the {@link InputStream} used to read data from the other end of the connection. */
    public InputStream getInputStream();

    /** @return the {@link OutputStream} used to write data to the other end of the connection. */
    public OutputStream getOutputStream();

    /** @return the RemoteAddress of the Socket as String */
    public String getRemoteAddress();

    public void dispose();
}
