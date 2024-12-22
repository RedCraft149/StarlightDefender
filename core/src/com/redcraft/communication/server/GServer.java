package com.redcraft.communication.server;

import com.redcraft.communication.channels.Channel;
import com.redcraft.communication.connections.LocalServerConnection;
import com.redcraft.communication.connections.ServerConnection;
import com.redcraft.communication.connections.SocketServerConnection;
import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.communication.sockets.NetServerSocket;
import com.redcraft.communication.sockets.ServerSocketHints;
import com.redcraft.rlib.function.Consumer;
import com.redcraft.rlib.function.Function;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class GServer<T extends GClientHandler> {
    protected ServerConnection serverConnection;
    protected Map<UUID,T> clients;
    protected Function<GServer<T>,T> clientSupplier;
    protected PacketList packetList;

    private Thread listeningThread;

    public GServer(Function<GServer<T>,T> type, ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
        this.serverConnection.setHandler(this::accept);
        this.clientSupplier = type;

        this.clients = new HashMap<>();
    }

    public GServer(Function<GServer<T>, T> clientSupplier, ServerConnection serverConnection, PacketList packetList) {
        this(clientSupplier,serverConnection);
        setPacketList(packetList);
    }

    public void start() {
        this.listeningThread = this.serverConnection.listenOnNewThread();
    }

    private UUID nextUUID() {
        Random random = new Random(System.nanoTime());
        byte[] name = new byte[16];
        random.nextBytes(name);
        return UUID.nameUUIDFromBytes(name);
    }

    public void forAll(Consumer<T> action) {
        for(T t : clients.values()) action.accept(t);
    }
    public void setPacketList(PacketList list) {
        this.packetList = list;
    }
    public PacketList availablePackets() {
        return packetList;
    }

    public boolean isRunning() {
        return serverConnection.isRunning();
    }

    public void stop() {
        serverConnection.stop();
        listeningThread.interrupt();
        for(ClientHandler handler : clients.values()) handler.stop();
        clients.clear();
    }

    public void accept(Channel channel) {
        UUID uuid = nextUUID();
        T handler = clientSupplier.run(this);
        handler.setUUID(uuid);
        handler.setChannel(channel);
        clients.put(uuid,handler);

        boolean success = handler.handleOnNewThread();
        if(!success) end(uuid);
        //else continuing on new thread
    }

    public void end(UUID uuid) {
        clients.get(uuid).stop();
        clients.remove(uuid);
    }

    public void send(Packet packet, UUID uuid) {
        if(!clients.containsKey(uuid)) return;
        clients.get(uuid).send(packet);
    }
    public void sendExcluding(Packet packet, UUID exclude) {
        for(Map.Entry<UUID,T> entry : clients.entrySet()) {
            if(!entry.getKey().equals(exclude)) entry.getValue().send(packet);
        }
    }
    public void sendToAll(Packet packet) {
        for(Map.Entry<UUID,T> entry : clients.entrySet()) {
            entry.getValue().send(packet);
        }
    }

    public static <T extends GClientHandler> GServer<T> net(PacketList list, Function<GServer<T>,T> type, int port) {
        ServerConnection serverConnection = new SocketServerConnection(list,null, new NetServerSocket(port,new ServerSocketHints()));
        return new GServer<>(type,serverConnection,list);
    }
    public static <T extends GClientHandler> GServer<T> local(Function<GServer<T>,T>type)  {
        ServerConnection serverConnection = new LocalServerConnection(null);
        return new GServer<>(type,serverConnection);
    }
    public static <T extends GClientHandler> GServer<T> local(Function<GServer<T>,T> type, PacketList list)  {
        ServerConnection serverConnection = new LocalServerConnection(null);
        return new GServer<>(type,serverConnection,list);
    }
}
