package com.demo.tiny_url.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationUtilTest {

    @Test
    public void testBase32Converter() {
        Assertions.assertEquals("1A",ApplicationUtil.toBase62(72));
    }
}
