package com.redcraft.communication.test;

import com.redcraft.communication.fields.GFieldPacket;

import java.util.Arrays;
import java.util.UUID;

public class GFieldPacketTest {
    public static void main(String[] args) {
        GFieldPacket packet = new GFieldPacket(new String[]{"He","llo","Wor","ld","!"},"H", UUID.randomUUID());
        byte[] b = packet.pack();
        GFieldPacket rec = new GFieldPacket();
        rec.unpack(b);
        System.out.println(Arrays.toString((String[]) rec.getContent()));
    }
}
