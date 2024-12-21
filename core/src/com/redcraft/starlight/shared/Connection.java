package com.redcraft.starlight.shared;

import com.redcraft.communication.connections.*;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.sockets.NetServerSocket;
import com.redcraft.communication.sockets.NetSocket;
import com.redcraft.communication.sockets.ServerSocketHints;


public class Connection {

    /**
     * Represents a connection from a client to a server within the same Java VM.
     * Can only be used in singleplayer, but is much more performant than NET or LOCALHOST.
     */
    public static final int LOCAL = 1;
    /**
     * Represents a connection through the internet, allowing general Multiplayer.
     */
    public static final int NET   = 2;
    /**
     * Represents a connection to the computers localhost address, allowing for LAN Multiplayer.
     * Can also represent a server connection.
     */
    public static final int LOCALHOST = 3;

    public String host;
    public int    port;
    public int    type;

    public Connection(String host, int port, int type) {
        this.host = host;
        this.port = port;
        this.type = type;
    }

    public String resolveHost() {
        if(type==LOCAL) throw new IllegalStateException("Local connection cannot have a host address.");
        if(type==NET) return host;
        if(type==LOCALHOST) return "localhost";
        throw new IllegalStateException("Connection is of unknown type.");
    }

    public int resolvePort() {
        if(type==LOCAL) throw new IllegalStateException("Local connection cannot have a port.");
        if(type==NET) return port;
        if(type==LOCALHOST) return port;
        throw new IllegalStateException("Connection is of unknown type.");
    }

    public String host() {
        return host;
    }
    public int port() {
        return port;
    }

    public int type() {
        return type;
    }

    public ServerConnection createServerConnection(PacketList available) {
        if(type==LOCAL) return new LocalServerConnection(null);
        if(type==NET || type==LOCALHOST) {
            return new SocketServerConnection(available,null, new NetServerSocket(resolvePort(), new ServerSocketHints()));
        }
        throw new IllegalStateException("Malformed connection.");
    }

    public ClientConnection createClientConnection(PacketList available) {
        if(type==LOCAL) {
            LocalClientConnection con = new LocalClientConnection(null);
            LocalConnection.connect(con.channel);
            return con;
        }
        if(type==NET ||  type==LOCALHOST) {
            return new SocketClientConnection(available,null,new NetSocket(resolveHost(),resolvePort(),null));
        }
        throw new IllegalStateException("Malformed connection.");
    }

    public static Connection net(String host, int port) {
        return new Connection(host,port,NET);
    }
    public static Connection local() {
        return new Connection(null,-1,LOCAL);
    }
    public static Connection localhost(int port) {
        return new Connection(null,port,LOCALHOST);
    }


}
