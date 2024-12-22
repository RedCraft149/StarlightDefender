package com.redcraft.communication.security;

import com.redcraft.communication.packets.Packet;

import java.security.PublicKey;


public class PublicKeyPacket implements Packet {

    PublicKey key;

    public PublicKeyPacket(PublicKey key) {
        this.key = key;
    }

    public PublicKeyPacket() {
    }

    @Override
    public int header() {
        return "internal:public_key".hashCode();
    }

    @Override
    public byte[] pack() {
        return key.getEncoded();
    }

    @Override
    public void unpack(byte[] bytes) {
        key = Security.createEncryptionKey(bytes);
    }

    @Override
    public Packet copy() {
        return new PublicKeyPacket(key);
    }
}
