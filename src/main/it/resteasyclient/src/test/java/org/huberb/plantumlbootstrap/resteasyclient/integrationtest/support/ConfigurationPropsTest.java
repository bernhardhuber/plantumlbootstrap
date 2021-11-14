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
package org.huberb.plantumlbootstrap.resteasyclient.integrationtest.support;

import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class ConfigurationPropsTest {

    private ConfigurationProps instance = null;

    @BeforeEach
    public void givenProperties_k1_v1_k2_v2() {
        this.instance = new ConfigurationProps(new Properties());
        this.instance.put("k1", "v1");
        this.instance.put("k2", "v2");
    }

    /**
     * Test of getProperties method, of class ConfigurationProps.
     */
    @Test
    public void testGetProperties() {
        Properties result = instance.getAllProperties();
        assertAll(
                () -> assertEquals("v1", result.getProperty("k1")),
                () -> assertEquals("v1", result.getProperty("k1", "X")),
                () -> assertEquals("v2", result.getProperty("k2")),
                () -> assertEquals("v2", result.getProperty("k2", "X")),
                () -> assertEquals("X", result.getProperty("k3", "X"))
        );
    }

    /**
     * Test of getPropertyOrDefault method, of class ConfigurationProps.
     */
    @Test
    public void testGetOrDefault_Object_Object() {
        assertAll(
                () -> assertEquals("v1", instance.getOrDefault("k1", "X")),
                () -> assertEquals("v2", instance.getOrDefault("k2", "X")),
                () -> assertEquals("X", instance.getOrDefault("k3", "X"))
        );
    }

    /**
     * Test of getPropertyOrDefault method, of class ConfigurationProps.
     */
    @Test
    public void testGetOrDefault_3args() {
        assertAll(
                () -> assertEquals("v1", instance.getOrDefault("k1", "X", String.class)),
                () -> assertEquals("v2", instance.getOrDefault("k2", "X", String.class)),
                () -> assertEquals("X", instance.getOrDefault("k3", "X", String.class))
        );
    }

    /**
     * Test of getPropertyOrDefault method, of class ConfigurationProps.
     */
    @Test
    public void testGetOrDefault_String_String() {
        assertAll(() -> assertEquals("v1", instance.getPropertyOrDefault("k1", "X")),
                () -> assertEquals("v2", instance.getPropertyOrDefault("k2", "X")),
                () -> assertEquals("X", instance.getPropertyOrDefault("k3", "X"))
        );
    }

    /**
     * Test of formatProps method, of class ConfigurationProps.
     */
    @Test
    public void testFormatAllProps() {
        String result = instance.formatAllProps();
        String m = String.format("formatProps %s", result);
        assertAll(
                () -> assertTrue(result.contains("k1=v1"), m),
                () -> assertTrue(result.contains("k2=v2"), m)
        );
    }

    /**
     * Test of formatProps method, of class ConfigurationProps.
     */
    @Test
    public void testFormatPrefixedProps() {
        String result = instance.formatPrefixedProps();
        String m = String.format("formatProps %s", result);
        assertAll(
                () -> assertTrue(result.contains("k1=v1"), m),
                () -> assertTrue(result.contains("k2=v2"), m)
        );
    }

}
