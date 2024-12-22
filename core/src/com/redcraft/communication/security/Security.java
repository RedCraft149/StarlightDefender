package com.redcraft.communication.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class Security {
    Cipher decrypt;
    PrivateKey decryptKey;
    Cipher encrypt;
    PublicKey encryptKey;
    String algorithm;

    MessageDigest digest;

    public Security() {
        algorithm = "RSA";
        setMessageDigest("SHA-256");
    }

    public void setEncryptionKey(PublicKey key) {
        try {
            encrypt = Cipher.getInstance(algorithm);
            encrypt.init(Cipher.ENCRYPT_MODE,key);
            encryptKey = key;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            encrypt = null;
            encryptKey = null;
        }
    }
    public void setDecryptionKey(PrivateKey key) {
        try {
            decrypt = Cipher.getInstance(algorithm);
            decrypt.init(Cipher.DECRYPT_MODE,key);
            decryptKey = key;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            decrypt = null;
            decryptKey = null;
        }
    }
    public void setEncryptionKey(byte[] key) {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            EncodedKeySpec spec = new X509EncodedKeySpec(key);
            setEncryptionKey(factory.generatePublic(spec));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            encrypt = null;
            encryptKey = null;
        }
    }

    public void setToKeyPair() {
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            return;
        }
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        setEncryptionKey(publicKey);
        setDecryptionKey(privateKey);
    }
    public void setMessageDigest(String algorithm) {
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            digest = null;
        }
    }

    public PublicKey getPublicKey() {
        return encryptKey;
    }

    public byte[] encrypt(byte[] data) {
        if(encrypt==null) throw new IllegalStateException("Encryption is not activated!");
        try {
            return encrypt.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            return null;
        }
    }

    public byte[] decrypt(byte[] data) {
        if(decrypt==null) throw new IllegalStateException("Decryption is not activated!");
        try {
            return decrypt.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] hash(String str) {
        return hash(str.getBytes());
    }
    public byte[] hash(byte[] bytes) {
        if(digest==null) throw new IllegalStateException("Hashing is not activated!");
        return digest.digest(bytes);
    }
    public boolean compareHashes(byte[] hashed, String str) {
        return Arrays.equals(hashed,hash(str));
    }

    public static PublicKey createEncryptionKey(byte[] key) {
        KeyFactory factory = null;
        try {
            factory = KeyFactory.getInstance("RSA");
            EncodedKeySpec spec = new X509EncodedKeySpec(key);
            return factory.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }
}
