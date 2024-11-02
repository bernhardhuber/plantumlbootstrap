/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.plantumlbootstrap.plantumlbootstrap.encoderdecoder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author berni3
 */
public class EncoderDecoderTest {

    final String decodedSample = "@startuml\n"
            + "Alice --> Bob: hello\n"
            + "@enduml";
    final String encodedSample = "Syp9J4vLqDMrKt3AJx9Io4ZDoSa70000";
    final EncoderDecoder encoderDecoder = new EncoderDecoder();

    @Test
    public void testEncode() {
        final String decoded = decodedSample;
        String result = encoderDecoder.encode(decoded);
        result = stripNewlines(result);
        System_out_format("testEncode%n"
                + "decoded: %s%n"
                + "result: %s%n", decoded, result);
        assertNotNull(result);
        assertEquals(encodedSample, result);
    }

    @Test
    public void testDecode() {
        final String encoded = encodedSample;
        String result = encoderDecoder.decode(encoded);
        result = stripNewlines(result);
        System_out_format("testDecode%n"
                + "encoded: %s%n"
                + "result: %s%n", encoded, result);
        assertNotNull(result);
        assertEquals(stripNewlines(decodedSample), result);
    }

    @Test
    public void testLoopingEncodeDecode() {
        final String input = "@start Lorem ipsum @end";
        final String unencoded = input;

        final String encoded1 = encoderDecoder.encode(unencoded);
        final String decoded1 = encoderDecoder.decode(encoded1);
        assertEquals(input, decoded1);

        final String encoded2 = encoderDecoder.encode(decoded1);
        final String decoded2 = encoderDecoder.decode(encoded2);
        assertEquals(input, decoded2);
    }

    String stripNewlines(String s) {
        String result = s.replaceAll("[\\r\\n]+", " ");
        return result;
    }

    void System_out_format(String format, Object... args) {
        System.out.format(format, args);
    }
}
