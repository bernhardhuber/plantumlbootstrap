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
public class ConfigurationProps {

    private final Properties props;

    public ConfigurationProps(Properties props) {
        this.props = props;
    }

    public Properties getProperties() {
        return this.props;
    }

    public Object getOrDefault(Object key, Object defaultValue) {
        return props.getOrDefault(key, defaultValue);
    }

    public <T> T getOrDefault(Object key, T defaultValue, Class<T> clazz) {
        return (T) props.getOrDefault(key, defaultValue);
    }

    public String getOrDefault(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static class ConfigurationPropsFactory {

        public ConfigurationProps create(
                Properties props,
                boolean systemProperties,
                File configurationPropsFile,
                String classpathResourceProperties
        ) throws IOException {
            final ConfigurationProps configurationProps = loadProperties(new Properties());
            final ConfigurationProps configurationPropsEmpty = loadProperties(new Properties());

            //---
            ConfigurationProps configurationPropsFromClasspath = configurationPropsEmpty;
            if (classpathResourceProperties != null) {
                configurationPropsFromClasspath = loadFromClasspath(classpathResourceProperties);
            }
            //---
            ConfigurationProps configurationPropsFromFile = configurationPropsEmpty;
            if (configurationPropsFile != null) {
                configurationPropsFromFile = loadFromFile(configurationPropsFile);
            }
            //---
            ConfigurationProps configurationPropsFromSystemProperties = configurationPropsEmpty;
            if (systemProperties) {
                configurationPropsFromSystemProperties = loadProperties(System.getProperties());
            }
            //---
            ConfigurationProps configurationPropsFromProperties = configurationPropsEmpty;
            if (props != null) {
                configurationPropsFromProperties = loadProperties(props);
            }

            // flatten by priority
            configurationPropsFromProperties.props.forEach((k, v) -> configurationProps.props.putIfAbsent(k, v)); // 400 -highest
            configurationPropsFromSystemProperties.props.forEach((k, v) -> configurationProps.props.putIfAbsent(k, v)); // 300 - high
            configurationPropsFromFile.props.forEach((k, v) -> configurationProps.props.putIfAbsent(k, v)); // 200 - low
            configurationPropsFromClasspath.props.forEach((k, v) -> configurationProps.props.putIfAbsent(k, v)); // 100 - lowest

            return configurationProps;
        }

        ConfigurationProps loadFromClasspath(String propsResourceName) throws IOException {
            final Properties props = new Properties();
            try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(propsResourceName)) {
                if (is != null) {
                    props.load(is);
                }
            }
            return new ConfigurationProps(props);
        }

        ConfigurationProps loadFromFile(File propsFile) throws IOException {
            final Properties props = new Properties();
            boolean guard = true;
            guard = guard && propsFile != null;
            guard = guard && propsFile.exists();
            guard = guard && propsFile.isFile();
            guard = guard && propsFile.canRead();
            if (guard) {
                try (InputStream is = new FileInputStream(propsFile)) {
                    props.load(is);
                }
            }
            return new ConfigurationProps(props);
        }

        ConfigurationProps loadProperties(Properties propsProps) {
            return new ConfigurationProps(propsProps);
        }

    }

}
