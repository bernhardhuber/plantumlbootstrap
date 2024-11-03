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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author berni3
 */
public class ConfigurationPropsBuilderTest {

    public ConfigurationPropsBuilderTest() {
    }

    @Test
    public void testCreate_K1_V1() throws IOException {
        final Properties props = new Properties();
        props.setProperty("propsK1", "propsV1");
        final File configurationPropsFile = null;
        final String classpathResourceProperties = null;
        final ConfigurationPropsBuilder instance = new ConfigurationPropsBuilder();
        instance
                .propsPrio400(props)
                .systemPropertiesPrio300(false)
                .filePrio200(configurationPropsFile)
                .classpathPrio100(classpathResourceProperties);
        //---
        final ConfigurationProps configurationProps = instance.build();
    }

    @Test
    public void testCreate_hierarchy(@TempDir Path tempDir) throws IOException {
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
        final ConfigurationPropsBuilder instance = new ConfigurationPropsBuilder();
        instance
                .propsPrio400(props)
                .systemPropertiesPrio300(loadFromSystemProps)
                .filePrio200(configurationPropsFile)
                .classpathPrio100(classpathResourceProperties);
        final ConfigurationProps configurationProps = instance.build();
        assertEquals("X", configurationProps.getPropertyOrDefault("propsX", "X"));

        System.out.printf("testCreate_hierarchy%n%s%n", configurationProps.formatPrefixedProps());
        Assertions.assertAll(
                () -> assertEquals("v01-props", configurationProps.getPropertyOrDefault("k01-props-systemProps-fileProps-classpathProps", "X")),
                () -> assertEquals("v02-props", configurationProps.getPropertyOrDefault("k02-props", "X")),
                () -> assertEquals("v02-systemProps", configurationProps.getPropertyOrDefault("k02-systemProps-fileProps-classpathProps", "X")),
                () -> assertEquals("v03-fileProps", configurationProps.getPropertyOrDefault("k03-fileProps-classpathProps", "X")),
                () -> assertEquals("v03-systemProps", configurationProps.getPropertyOrDefault("k03-systemProps", "X")),
                () -> assertEquals("v04-classpathProps", configurationProps.getPropertyOrDefault("k04-classpathProps-4", "X")),
                () -> assertEquals("v04-fileProps", configurationProps.getPropertyOrDefault("k04-fileProps", "X"))
        );
    }

    @Test
    public void testCreate_hierarchy_prefix_k(@TempDir Path tempDir) throws IOException {
        //---
        final Properties props = new Properties();
        props.setProperty("k01-props-systemProps-fileProps-classpathProps", "v01-props");
        props.setProperty("k02-props", "v02-props");
        props.setProperty("l01-props", "v01-props-for-l");
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
        final ConfigurationPropsBuilder instance = new ConfigurationPropsBuilder();
        instance
                .prefix("k")
                .propsPrio400(props)
                .systemPropertiesPrio300(loadFromSystemProps)
                .filePrio200(configurationPropsFile)
                .classpathPrio100(classpathResourceProperties);
        final ConfigurationProps configurationProps = instance.build();
        assertEquals("X", configurationProps.getPropertyOrDefault("propsX", "X"));
        assertEquals("X", configurationProps.getPropertyOrDefault("l01-props", "X"));

        System.out.printf("testCreate_hierarchy_prefix_k%n%s%n", configurationProps.formatPrefixedProps());
        Assertions.assertAll(
                () -> assertEquals("v01-props", configurationProps.getPropertyOrDefault("k01-props-systemProps-fileProps-classpathProps", "X")),
                () -> assertEquals("v02-props", configurationProps.getPropertyOrDefault("k02-props", "X")),
                () -> assertEquals("v02-systemProps", configurationProps.getPropertyOrDefault("k02-systemProps-fileProps-classpathProps", "X")),
                () -> assertEquals("v03-fileProps", configurationProps.getPropertyOrDefault("k03-fileProps-classpathProps", "X")),
                () -> assertEquals("v03-systemProps", configurationProps.getPropertyOrDefault("k03-systemProps", "X")),
                () -> assertEquals("v04-classpathProps", configurationProps.getPropertyOrDefault("k04-classpathProps-4", "X")),
                () -> assertEquals("v04-fileProps", configurationProps.getPropertyOrDefault("k04-fileProps", "X"))
        );
    }

}
