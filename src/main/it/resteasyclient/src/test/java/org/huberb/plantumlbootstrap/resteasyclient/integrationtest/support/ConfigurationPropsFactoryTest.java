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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import org.huberb.plantumlbootstrap.resteasyclient.integrationtest.support.ConfigurationProps.ConfigurationPropsFactory;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class ConfigurationPropsFactoryTest {

    public ConfigurationPropsFactoryTest() {
    }

    @Test
    public void testCreate_K1_V1() throws IOException {
        final Properties props = new Properties();
        props.setProperty("propsK1", "propsV1");
        final File configurationPropsFile = null;
        final String classpathResourceProperties = null;
        final ConfigurationPropsFactory instance = new ConfigurationPropsFactory();
        //---
        final ConfigurationProps configurationProps = instance.create(
                props,
                false,
                configurationPropsFile,
                classpathResourceProperties);
        assertEquals("propsV1", configurationProps.getOrDefault("propsK1", "X"));
        assertEquals("X", configurationProps.getOrDefault("propsX", "X"));
    }

    @Test
    public void testCreate_Hierarchy(@TempDir Path tempDir) throws IOException {
        //---
        final Properties props = new Properties();
        props.setProperty("k01-props-systemProps-fileProps-classpathProps", "v01-props");
        props.setProperty("k02-props", "v02-props");
        //---
        final boolean loadFromSystemProps = true;
        System.getProperties().put("k01-props-systemProps-fileProps-classpathProps", "v01-systemProps");
        System.getProperties().put("k02-systemProps-fileProps-classpathProps", "v02-systemProps");
        System.getProperties().put("k03-systemProps", "v03-systemProps");
        //---
        final Path filePropsPath = tempDir.resolve("fileProps.properties");
        final File configurationPropsFile = filePropsPath.toFile();
        final Properties fileProps = new Properties();
        fileProps.setProperty("k01-props-systemProps-fileProps-classpathProps", "v01-fileProps");
        fileProps.setProperty("k02-systemProps-fileProps-classpathProps", "v02-fileProps");
        fileProps.setProperty("k03-fileProps-classpathProps", "v03-fileProps");
        fileProps.setProperty("k04-fileProps", "v04-fileProps");
        try (FileWriter fw = new FileWriter(configurationPropsFile)) {
            fileProps.store(fw, "unit-test ConfigurationPropsFactoryTest");
        }
        //---
        final String classpathResourceProperties = "configurationPropsFactoryTest.properties";
        //---
        final ConfigurationPropsFactory instance = new ConfigurationPropsFactory();
        final ConfigurationProps configurationProps = instance.create(
                props,
                loadFromSystemProps,
                configurationPropsFile,
                classpathResourceProperties);
        assertEquals("X", configurationProps.getOrDefault("propsX", "X"));

        System.out.printf("testCreate_XXX%n");
        configurationProps.getProperties().entrySet().stream()
                .filter((e) -> e.getKey().toString().startsWith("k"))
                .sorted((e1, e2) -> e1.getKey().toString().compareTo(e2.getKey().toString()))
                .forEach((e) -> {
                    System.out.printf("%s=%s%n", e.getKey(), e.getValue());
                }
                );
        Assertions.assertAll(
                () -> assertEquals("v01-props", configurationProps.getOrDefault("k01-props-systemProps-fileProps-classpathProps", "X")),
                () -> assertEquals("v02-props", configurationProps.getOrDefault("k02-props", "X")),
                () -> assertEquals("v02-systemProps", configurationProps.getOrDefault("k02-systemProps-fileProps-classpathProps", "X")),
                () -> assertEquals("v03-fileProps", configurationProps.getOrDefault("k03-fileProps-classpathProps", "X")),
                () -> assertEquals("v03-systemProps", configurationProps.getOrDefault("k03-systemProps", "X")),
                () -> assertEquals("v04-classpathProps", configurationProps.getOrDefault("k04-classpathProps-4", "X")),
                () -> assertEquals("v04-fileProps", configurationProps.getOrDefault("k04-fileProps", "X"))
        );
    }

}
