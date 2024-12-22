package com.redcraft.communication.packets.handlers;

import com.redcraft.rlib.actions.Action;
import com.redcraft.rlib.actions.VoidAction;

public class GPacketAction {
    private final Action action;
    private final String[] indices;
    private final int length;

    public GPacketAction(Action action, String... indices) {
        this.action = action;
        this.indices = indices;
        this.length = indices.length;
    }

    public Action getAction() {
        return action;
    }

    public String[] getIndices() {
        return indices;
    }
    public String getIndex(int i) {
        return indices[i];
    }
    public int getLength() {
        return length;
    }

    public static GPacketAction create(VoidAction.n0 action) {
        return new GPacketAction(action,new String[0]);
    }
    public static <A> GPacketAction create(VoidAction.n1<A> action, String... indices) {
        return new GPacketAction(action,indices);
    }
    public static <A,B> GPacketAction create(VoidAction.n2<A,B> action, String... indices) {
        return new GPacketAction(action,indices);
    }
    public static <A,B,C> GPacketAction create(VoidAction.n3<A,B,C> action, String... indices) {
        return new GPacketAction(action,indices);
    }
    public static <A,B,C,D> GPacketAction create(VoidAction.n4<A,B,C,D> action, String... indices) {
        return new GPacketAction(action,indices);
    }
    public static <A,B,C,D,E> GPacketAction create(VoidAction.n5<A,B,C,D,E> action, String... indices) {
        return new GPacketAction(action,indices);
    }
    public static <A,B,C,D,E,F> GPacketAction create(VoidAction.n6<A,B,C,D,E,F> action, String... indices) {
        return new GPacketAction(action,indices);
    }
    public static <A,B,C,D,E,F,G> GPacketAction create(VoidAction.n7<A,B,C,D,E,F,G> action, String... indices) {
        return new GPacketAction(action,indices);
    }
    public static <A,B,C,D,E,F,G,H> GPacketAction create(VoidAction.n8<A,B,C,D,E,F,G,H> action, String... indices) {
        return new GPacketAction(action,indices);
    }


}
