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

/**
 * Configuration properties holds configuration property key value pairs.
 * <p>
 * Optionally you can define a prefix (aka namespace) for grouping the keys.
 * </p>
 *
 * @author berni3
 */
public class ConfigurationProps {

    private final String prefix;
    private final Properties props;

    public ConfigurationProps(String prefix) {
        this(prefix, new Properties());
    }

    public ConfigurationProps(Properties props) {
        this("", props);
    }

    public ConfigurationProps(String prefix, Properties props) {
        if (prefix == null) {
            prefix = "";
        }
        this.prefix = prefix;
        this.props = props;
    }

    //---
    public Properties getAllProperties() {
        return this.props;
    }

    public void putIfAbsent(Object k, Object v) {
        props.putIfAbsent(k, v);
    }

    public void put(Object k, Object v) {
        props.put(k, v);
    }

//    public Object getOrDefault(Object key, Object defaultValue) {
//        return props.getOrDefault(key, defaultValue);
//    }
    public <T> T getOrDefault(Object key, T defaultValue) {
        return (T) props.getOrDefault(key, defaultValue);
    }

    public String getPropertyOrDefault(String key, String defaultValue) {
        final String result;
        final String keyForQuery = calcPrefixedKey(key);
        result = props.getProperty(keyForQuery, defaultValue);
        return result;
    }

    String calcPrefixedKey(String key) {
        final String keyForQuery;
        if (prefix != null && !prefix.isEmpty()
                && !key.startsWith(prefix)) {
            keyForQuery = prefix + key;
        } else {
            keyForQuery = key;
        }
        return keyForQuery;
    }

    /**
     * Return all key/value pairs regardless of its prefix.
     *
     * @return
     */
    public String formatAllProps() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("prefix: %s%n", prefix));
        props.entrySet().stream()
                .sorted((e1, e2) -> e1.getKey().toString().compareTo(e2.getKey().toString()))
                .forEach(e -> sb.append(String.format("%s=%s%n", e.getKey(), e.getValue())));
        return sb.toString();
    }

    /**
     * Return only key/value pairs where key starts with the prefix.
     *
     * @return
     */
    public String formatPrefixedProps() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("prefix: %s%n", prefix));
        props.entrySet().stream()
                .filter(e -> e.getKey().toString().startsWith(prefix))
                .sorted((e1, e2) -> e1.getKey().toString().compareTo(e2.getKey().toString()))
                .forEach(e -> sb.append(String.format("%s=%s%n", e.getKey(), e.getValue())));
        return sb.toString();
    }
}
