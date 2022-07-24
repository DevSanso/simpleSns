package com.github.devsanso.sns.dto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringDto {
    private String str;
    public StringDto(String str) {
        this.str = str;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public String toSha256HexString() {
        MessageDigest md = null;
       try {
           md = MessageDigest.getInstance("SHA-256");
           md.update(str.getBytes());
       }catch(Exception e) {}
        return bytesToHex(md.digest());
    }
}
