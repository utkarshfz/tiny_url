package com.demo.tiny_url.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringSerializerTest {

    @Test
    public void shouldSerializeAndDeserializeTest() {
        String testString = "test";
        StringSerializer serializer = new StringSerializer();
        byte[] serializedString = serializer.serialize(testString);
        Assertions.assertEquals(testString, serializer.deserialize(serializedString));
    }
}
