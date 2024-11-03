/*
 * Copyright 2024 pi.
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
package org.huberb.plantumlbootstrap.plantumlbootstrap.support;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author pi
 */
public class StringUtilityTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void testIsStringEmptyTrue(String s) {
        assertTrue(StringUtility.isStringEmpty(s), s);
        assertFalse(StringUtility.isStringNotEmpty(s), s);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "AA", "0", "a", " A ", "\t", " ", "\r", "\n"})
    void testIsStringEmptyFalse(String s) {
        assertFalse(StringUtility.isStringEmpty(s), s);
        assertTrue(StringUtility.isStringNotEmpty(s), s);
    }
}
