package com.redcraft.communication.sockets;

public class ServerSocketHints {
    /** The listen backlog length. Needs to be greater than 0, otherwise the system default is used. backlog is the maximum queue
     * length for incoming connection, i.e. maximum number of connections waiting for accept(...). If a connection indication
     * arrives when the queue is full, the connection is refused. */
    public int backlog = 16;

    /** Performance preferences are described by three integers whose values indicate the relative importance of short connection
     * time, low latency, and high bandwidth. The absolute values of the integers are irrelevant; in order to choose a protocol the
     * values are simply compared, with larger values indicating stronger preferences. Negative values represent a lower priority
     * than positive values. If the application prefers short connection time over both low latency and high bandwidth, for
     * example, then it could invoke this method with the values (1, 0, 0). If the application prefers high bandwidth above low
     * latency, and low latency above short connection time, then it could invoke this method with the values (0, 1, 2). */
    public int performancePrefConnectionTime = 0;
    /** See performancePrefConnectionTime for details. */
    public int performancePrefLatency = 1; // low latency
    /** See performancePrefConnectionTime for details. */
    public int performancePrefBandwidth = 0;
    /** Enable/disable the SO_REUSEADDR socket option. */
    public boolean reuseAddress = true;
    /** The SO_TIMEOUT in milliseconds for how long to wait during server.accept(). Enter 0 for infinite wait. */
    public int acceptTimeout = 0;
    /** The SO_RCVBUF (receive buffer) size in bytes for server.accept(). */
    public int receiveBufferSize = 4096;
}
