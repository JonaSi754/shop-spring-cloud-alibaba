package org.sijinghua.shop.utils.md5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {
    private static final Logger logger = LoggerFactory.getLogger(MD5Hash.class);

    public static String md5Java(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));

            // converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            digest = sb.toString();
        // } catch (UnsupportedEncodingException e) {
            // logger.error("Unsupported encoding: ", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("No such algorithm: ", e);
        }
        return digest;
    }
}
