package com.redcraft.communication.security;

import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.packets.PacketList;
import com.redcraft.rlib.serial.TypeConversions;


public class EncryptedPacket implements Packet {

    byte[] encrypted;

    public EncryptedPacket(byte[] encrypted) {
        this.encrypted = encrypted;
    }

    public EncryptedPacket() {
        encrypted = new byte[0];
    }

    @Override
    public int header() {
        return "internal:encrypted_packet".hashCode();
    }

    @Override
    public byte[] pack() {
        return encrypted;
    }

    @Override
    public void unpack(byte[] bytes) {
        this.encrypted = bytes;
    }

    public Packet decrypt(Security security, PacketList list) {
        byte[] decrypted = security.decrypt(encrypted);
        int subheader = TypeConversions.bytesToInt(decrypted,0);
        byte[] content = new byte[decrypted.length-4];
        System.arraycopy(decrypted,4,content,0,content.length);

        Packet packet = list.newPacket(subheader);
        packet.unpack(content);

        return packet;
    }

    @Override
    public Packet copy() {
        byte[] copy = new byte[encrypted.length];
        System.arraycopy(encrypted,0,copy,0,encrypted.length);
        return new EncryptedPacket(copy);
    }

    public static EncryptedPacket encrypt(Packet packet, Security security) {
        byte[] data = packet.pack();
        byte[] packed = new byte[data.length+4];
        System.arraycopy(TypeConversions.intToBytes(packet.header()),0,packed,0,4);
        System.arraycopy(data,0,packed,4,data.length);
        byte[] encrypted = security.encrypt(packed);
        return new EncryptedPacket(encrypted);
    }
}
