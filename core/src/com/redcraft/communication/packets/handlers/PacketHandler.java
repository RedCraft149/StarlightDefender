package com.redcraft.communication.packets.handlers;


import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.function.Consumer;

public interface PacketHandler<T extends Packet> {

    int IGNORE_HEADER = 0;

    void receive(T t);
    default boolean accept(Packet packet) {
        if(packet.header() != header() && header() != IGNORE_HEADER) return false;
        try {
            @SuppressWarnings("unchecked")
            T t = (T) packet;
            receive(t);
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }
    int header();

    static <X extends Packet> PacketHandler<X> of(Consumer<X> action, int header) {
        return new PacketHandler<X>() {
            @Override
            public void receive(X x) {
                action.accept(x);
            }

            public int header() {
                return header;
            }
        };
    }

    static <X extends Packet> PacketHandler<X> of(Consumer<X> action) {
        return of(action,IGNORE_HEADER);
    }
}
