package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.PacketList;
import com.redcraft.starlight.shared.MessagePacket;
import com.redcraft.communication.packets.GPacket;
import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.function.Supplier;

public class StarlightDefenderPacketList {
    private static final PacketList packetList;

    static {

        packetList = new PacketList();
        addAvailablePacket(ChunkCreationPacket::new);
        addAvailablePacket(EntityPositionPacket::new);
        addAvailablePacket(EntityRemovePacket::new);
        addAvailablePacket(PlayerRotationPacket::new);
        addAvailablePacket(UniverseCreationPacket::new);
        addAvailablePacket(()->new MessagePacket(null));
        addAvailablePacket(EntityHealthPacket::new);
        addAvailablePacket(EntityRotationPacket::new);
        addAvailablePacket(PlayerPausePacket::new);
        addAvailablePacket(PauseTimerPacket::new);
        addAvailablePacket(HeartbeatPacket::new);

        addAvailablePacket(GPacket.parse("name:player_position, float:x, float:y"));
        addAvailablePacket(GPacket.parse("name:player_shoot"));
        addAvailablePacket(GPacket.parse("name:entity_creation, uuid:id, float:x, float:y, float:rotation, int:type"));
        addAvailablePacket(GPacket.parse("name:player_score, int:score"));
        addAvailablePacket(GPacket.parse("name:player_shield_hit, float:x, float:y, float:time"));
        addAvailablePacket(GPacket.parse("name:player_shield_time, float:time, boolean:active"));
        addAvailablePacket(GPacket.parse("name:player_raise_shields"));
        addAvailablePacket(GPacket.parse("name:player_ammo, int:ammo"));
        addAvailablePacket(GPacket.parse("name:space_station_destroyed, boolean:all"));
    }

    public static void addAvailablePacket(Supplier<Packet> supplier) {
        packetList.addPacket(supplier);
    }

    public static PacketList getAvailablePackets() {
        return packetList;
    }
    public static Packet newPacket(int header) {
        return packetList.newPacket(header);
    }
    public static GPacket newPacket(String header) {
        return (GPacket) newPacket(header.hashCode());
    }

    public static final int MESSAGE_PACKET =        0x01;

    public static final int CHUNK_CREATION =        0x10;
    public static final int UNIVERSE_CREATION =     0x11;

    public static final int ENTITY_CREATION =       0x20;
    public static final int ENTITY_POSITION =       0x21;
    public static final int ENTITY_REMOVE =         0x22;
    public static final int ENTITY_HEALTH =         0x23;
    public static final int ENTITY_ROTATION =       0x24;

    public static final int PLAYER_POSITION =       0x30;
    public static final int PLAYER_ROTATION =       0x31;
    public static final int BULLET_SHOOT =          0x32;
    public static final int PLAYER_DEATH =          0x33;
    public static final int PAUSE_TIMER =           0x34;
    public static final int PLAYER_SCORE =          0x35;

}
