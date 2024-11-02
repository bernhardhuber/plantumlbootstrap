/*
 * Copyright 2021 berni3.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.huberb.plantumlbootstrap.plantumlbootstrap.encoderdecoder;

import org.huberb.plantumlbootstrap.plantumlbootstrap.encoderdecoder.PumlDefaultEncodedDecoded;
import org.huberb.plantumlbootstrap.plantumlbootstrap.encoderdecoder.EncoderDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author berni3
 */
@ExtendWith(MockitoExtension.class)
public class PumlDefaultEncodedDecodedTest {

    @Spy
    private EncoderDecoder encoderDecoder;

    @InjectMocks
    PumlDefaultEncodedDecoded instance;

    @BeforeEach
    public void setUp() {
        instance.postConstruct();
    }

    /**
     * Test of retrievePumlDefaultDecoded method, of class
     * PumlDefaultEncodedDecoded.
     */
    @Test
    public void testRetrievePumlDefaultDecoded() {
        final String expResult = "@startuml\n"
                + "Alice --> Bob : hello\n"
                + "@enduml";
        final String result = instance.retrievePumlDefaultDecoded();
        assertEquals(stripLineEndings(expResult), stripLineEndings(result));
    }

    String stripLineEndings(String s) {
        final String stripped = s
                .replace("\n", "")
                .replace("\r", "");
        return stripped;
    }

    /**
     * Test of retrievePumlDefaultEncoded method, of class
     * PumlDefaultEncodedDecoded.
     */
    @Test
    public void testRetrievePumlDefaultEncoded() {
        String expResult = "Syp9J4vLqDMrKt3AJrAmKiX8pSd91m00";
        String result = instance.retrievePumlDefaultEncoded();
        assertEquals(expResult, result);
    }

}
