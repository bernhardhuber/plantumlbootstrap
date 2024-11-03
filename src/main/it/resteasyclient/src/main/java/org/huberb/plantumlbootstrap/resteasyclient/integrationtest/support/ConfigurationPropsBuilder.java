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
    private Properties configurationProps400FromProperties = configurationPropsEmpty;
    private Properties configurationProps300FromSystemProperties = configurationPropsEmpty;
    private Properties configurationProps200FromFile = configurationPropsEmpty;
    private Properties configurationProps100FromClasspath = configurationPropsEmpty;
    private String prefix;

    public ConfigurationPropsBuilder prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public ConfigurationPropsBuilder propsPrio400(Properties props) {
        //---
        if (props != null) {
            configurationProps400FromProperties = loadProperties(props);
        }
        return this;
    }

    public ConfigurationPropsBuilder systemPropertiesPrio300(boolean systemProperties) {
        //---
        if (systemProperties) {
            configurationProps300FromSystemProperties = loadProperties(System.getProperties());
        }
        return this;
    }

    public ConfigurationPropsBuilder filePrio200(File configurationPropsFile) throws IOException {
        //---
        if (configurationPropsFile != null) {
            configurationProps200FromFile = loadFromFile(configurationPropsFile);
        }
        return this;
    }

    public ConfigurationPropsBuilder classpathPrio100(String classpathResourceProperties) throws IOException {
        //---
        if (classpathResourceProperties != null) {
            configurationProps100FromClasspath = loadFromClasspath(classpathResourceProperties);
        }
        return this;
    }

    public ConfigurationProps build() {
        final ConfigurationProps configurationProps = new ConfigurationProps(this.prefix);
        // flatten by priority
        // filter by prefix?
        configurationProps400FromProperties.forEach((k, v) -> configurationProps.putIfAbsent(k, v)); // 400 -highest
        configurationProps300FromSystemProperties.forEach((k, v) -> configurationProps.putIfAbsent(k, v)); // 300 - high
        configurationProps200FromFile.forEach((k, v) -> configurationProps.putIfAbsent(k, v)); // 200 - low
        configurationProps100FromClasspath.forEach((k, v) -> configurationProps.putIfAbsent(k, v)); // 100 - lowest
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
        boolean guard =  propsFile != null;
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
