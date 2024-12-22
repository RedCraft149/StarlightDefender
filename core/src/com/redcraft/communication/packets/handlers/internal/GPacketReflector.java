package com.redcraft.communication.packets.handlers.internal;

import com.redcraft.communication.packets.GPacket;

public interface GPacketReflector {
    boolean reflect(GPacket packet);
}
