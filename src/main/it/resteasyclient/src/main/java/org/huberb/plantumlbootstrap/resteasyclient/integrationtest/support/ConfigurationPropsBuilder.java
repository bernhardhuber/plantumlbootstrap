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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author berni3
 */
public class ConfigurationPropsBuilder {

    private final Properties configurationPropsEmpty = loadProperties(new Properties());
    private Properties configurationPropsFromProperties = configurationPropsEmpty;
    private Properties configurationPropsFromSystemProperties = configurationPropsEmpty;
    private Properties configurationPropsFromFile = configurationPropsEmpty;
    private Properties configurationPropsFromClasspath = configurationPropsEmpty;
    private String prefix;

    public ConfigurationPropsBuilder prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public ConfigurationPropsBuilder props400(Properties props) {
        //---
        if (props != null) {
            configurationPropsFromProperties = loadProperties(props);
        }
        return this;
    }

    public ConfigurationPropsBuilder systemProperties300(boolean systemProperties) {
        //---
        if (systemProperties) {
            configurationPropsFromSystemProperties = loadProperties(System.getProperties());
        }
        return this;
    }

    public ConfigurationPropsBuilder file200(File configurationPropsFile) throws IOException {
        //---
        if (configurationPropsFile != null) {
            configurationPropsFromFile = loadFromFile(configurationPropsFile);
        }
        return this;
    }

    public ConfigurationPropsBuilder classpath100(String classpathResourceProperties) throws IOException {
        //---
        if (classpathResourceProperties != null) {
            configurationPropsFromClasspath = loadFromClasspath(classpathResourceProperties);
        }
        return this;
    }

    public ConfigurationProps build() {
        final ConfigurationProps configurationProps = new ConfigurationProps(this.prefix);
        // flatten by priority
        configurationPropsFromProperties.forEach((k, v) -> configurationProps.putIfAbsent(k, v)); // 400 -highest
        configurationPropsFromSystemProperties.forEach((k, v) -> configurationProps.putIfAbsent(k, v)); // 300 - high
        configurationPropsFromFile.forEach((k, v) -> configurationProps.putIfAbsent(k, v)); // 200 - low
        configurationPropsFromClasspath.forEach((k, v) -> configurationProps.putIfAbsent(k, v)); // 100 - lowest
        return configurationProps;
    }

    //---
    Properties loadFromClasspath(String propsResourceName) throws IOException {
        final Properties props = new Properties();
        try (final InputStream is = this.getClass().getClassLoader().getResourceAsStream(propsResourceName)) {
            if (is != null) {
                props.load(is);
            }
        }
        return props;
    }

    Properties loadFromFile(File propsFile) throws IOException {
        final Properties props = new Properties();
        boolean guard = true;
        guard = guard && propsFile != null;
        guard = guard && propsFile.exists();
        guard = guard && propsFile.isFile();
        guard = guard && propsFile.canRead();
        if (guard) {
            try (final InputStream is = new FileInputStream(propsFile)) {
                props.load(is);
            }
        }
        return props;
    }

    Properties loadProperties(Properties propsProps) {
        return propsProps;
    }

}
