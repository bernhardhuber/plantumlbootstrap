/*
 * Copyright 2021 berni3.
 *
 * Licensed under the Apache License, ApplAndPlantumlVersion 2.0 (the "License");
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
package org.huberb.plantumlbootstrap.plantumlbootstrap.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import net.sourceforge.plantuml.version.Version;

/**
 *
 * @author berni3
 */
@ApplicationScoped
public class ApplAndPlantumlVersion {

    private static Logger LOGGER = Logger.getLogger(ApplAndPlantumlVersion.class.getName());

    /**
     * Retrieve version information about this application.
     *
     * @return
     */
    String retrieveProjectFullDescription() {
        final String unknown = "-unknown-";
        final String versionproperties = "version.properties";

        String projectFullDescription = unknown;
        try (InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(versionproperties)) {
            if (inStream != null) {
                final Properties prop = new Properties();
                prop.load(inStream);
                projectFullDescription = prop.getProperty("projectFullDescription", unknown);
            } else {
                LOGGER.log(Level.INFO, String.format("Cannot load %s resource", versionproperties));
            }
        } catch (IOException ioex) {
            LOGGER.log(Level.INFO, String.format("Cannot load %s resource", versionproperties), ioex);
        }
        return projectFullDescription;
    }

    /**
     * Retrieve version information about plantuml jar.
     *
     * @return
     */
    String retrievePlantumlFullDescription() {
        final String fullDescription = Version.fullDescription();
        return fullDescription;
    }
}
