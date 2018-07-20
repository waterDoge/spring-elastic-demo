package com.ata.elastic.strategy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.function.BiFunction;

public enum PasswordStrategies {
    SHA_256("SHA-256");

    private BiFunction<byte[], byte[], String> algorithm;

    private String name;

    PasswordStrategies(String name, BiFunction<byte[], byte[], String> algorithm) {
        this.algorithm = algorithm;
        this.name = name;
    }

    PasswordStrategies(final String algorithm) {
        name = algorithm;

        this.algorithm = (bytes, bytes2) -> {
            byte[] raw = new byte[bytes.length + bytes2.length];
            System.arraycopy(bytes, 0, raw, 0, bytes.length);
            System.arraycopy(bytes2, 0, raw, bytes.length, bytes2.length);
            try {
                final byte[] digest = MessageDigest.getInstance(algorithm).digest(raw);
                return Base64.getEncoder().encodeToString(digest);
            } catch (NoSuchAlgorithmException e) {
                throw new Error(e);
            }
        };
    }

    public String generatePassword(byte[] rawPassword, byte[] salt) {
        return algorithm.apply(rawPassword, salt);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {

    }
}
