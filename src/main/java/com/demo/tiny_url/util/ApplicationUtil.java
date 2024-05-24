package com.demo.tiny_url.util;


public class ApplicationUtil {

    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = BASE62_CHARS.length();

    /**
     * Converts long value to Base62 format
     * @param value long value
     * @return base62 representation
     */
    public static String toBase62(long value) {
        StringBuilder result = new StringBuilder();

        do {
            result.insert(0, BASE62_CHARS.charAt((int) (value % BASE)));
            value /= BASE;
        } while (value > 0);

        return result.toString();
    }

}
