package com.demo.tiny_url.constants;

public class TinyUrlConstants {
    public static final long CUSTOM_URL_ID_LENGTH_LIMIT = 16;
    public static final long URL_LENGTH_LIMIT = 250;

    //should not include base62 characters
    public static final String SHORT_URL_SALT = "!";

    public static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
}
