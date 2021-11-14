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
package org.huberb.plantumlbootstrap.resteasyclient.integrationtest;

import org.huberb.plantumlbootstrap.resteasyclient.integrationtest.support.ConfigurationPropsFactory;
import java.io.IOException;
import org.huberb.plantumlbootstrap.resteasyclient.integrationtest.support.ConfigurationProps;

/**
 *
 * @author berni3
 */
class ConfigurationPropsFactoryForPlantumlbootstrap {
    
    final String prefix = "plantumlbootstrap.";
    final String resourceName = "configuration.properties";
    final String systemPropertyFile = "configurationPropertyFile";

    private ConfigurationPropsFactoryForPlantumlbootstrap() {
    }
    public static final ConfigurationPropsFactoryForPlantumlbootstrap INSTANCE = new ConfigurationPropsFactoryForPlantumlbootstrap();

    public static ConfigurationProps create() throws IOException {
        ConfigurationPropsFactory xx = new ConfigurationPropsFactory(INSTANCE.prefix, INSTANCE.resourceName, INSTANCE.systemPropertyFile) {
        };
        return xx.create();
    }
    
}
