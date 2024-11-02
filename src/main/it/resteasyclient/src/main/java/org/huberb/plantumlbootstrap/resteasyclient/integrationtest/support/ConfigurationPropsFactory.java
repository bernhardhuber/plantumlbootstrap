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
import java.io.IOException;
import java.util.Properties;

/**
 * A factory for creating a {@link ConfigurationProps} instance.
 *
 * @author berni3
 */
public class ConfigurationPropsFactory {

    final String prefix;
    final String resourceName;
    final String systemPropertyFile;

    /**
     * Create using these input sources.
     *
     * @param prefix
     * @param resourceName
     * @param systemPropertyFile
     */
    public ConfigurationPropsFactory(String prefix, String resourceName, String systemPropertyFile) {
        this.prefix = prefix;
        this.resourceName = resourceName;
        this.systemPropertyFile = systemPropertyFile;
    }

    /**
     * Create {@link ConfigurationProps}.
     *
     * @return
     * @throws IOException
     */
    public ConfigurationProps create() throws IOException {
        final Properties props = new Properties();
        final boolean systemProperties = true;
        final File configurationPropsFile;
        final String configurationPropertyFile = System.getProperty(systemPropertyFile);
        if (configurationPropertyFile != null) {
            configurationPropsFile = new File(configurationPropertyFile);
        } else {
            configurationPropsFile = null;
        }
        final String classpathResourceProperties = resourceName;
        final ConfigurationPropsBuilder instance = new ConfigurationPropsBuilder();
        instance
                .prefix(prefix)
                .propsPrio400(props)
                .systemPropertiesPrio300(systemProperties)
                .filePrio200(configurationPropsFile)
                .classpathPrio100(classpathResourceProperties);
        return instance.build();
    }

}
