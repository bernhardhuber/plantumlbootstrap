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

import java.io.IOException;
import java.util.function.Predicate;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.huberb.plantumlbootstrap.resteasyclient.integrationtest.EncoderDecoderResourceIT.ResteasyClientAutoCloseable;
import org.huberb.plantumlbootstrap.resteasyclient.integrationtest.support.ConfigurationProps;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author berni3
 */
public class ApplAndPlantumlVersionResourceIT {

    static String URL_BASE = "http://localhost:8081/plantumlbootstrap-1.0-SNAPSHOT";

    @BeforeAll
    public static void setUpConfigurationProps() throws IOException {
        final ConfigurationProps configurationProps = ConfigurationPropsFactoryForPlantumlbootstrap.create();
        URL_BASE = configurationProps.getPropertyOrDefault("urlBase", URL_BASE);
        final String m = "" + URL_BASE;
        Assumptions.assumeTrue(URL_BASE.startsWith("http"), m);
    }

    /**
     * Test method encodeGet of ApplAndPlantumlVersionResource.
     */
    @Test
    public void testApplAndPlantumlVersion() {
        final String url = URL_BASE + "/webresources/applAndPlantumlVersion/applAndPlantumlVersion";

        final ResteasyClient resteasyClient = new ResteasyClientBuilderImpl()
                .build();
        try (final ResteasyClientAutoCloseable rcac = new ResteasyClientAutoCloseable(resteasyClient)) {
            final ResteasyWebTarget resteasyWebTarget = resteasyClient.target(UriBuilder.fromPath(url));
            // GET
            try (final Response response = resteasyWebTarget
                    .request()
                    .accept(MediaType.TEXT_PLAIN)
                    .get()) {
                final String entityRead = EntityReadSupport.retrieveEntityReadFromResponse("testApplAndPlantumlVersion", response);
                final String m = "" + response;
                assertEquals(Status.OK.getStatusCode(), response.getStatus(), m);

                assertAll(
                        //Project version: plantumlbootstrap-1.0-SNAPSHOT
                        //Plantuml version: PlantUML version 1.2021.12 (Tue Oct 05 18:01:58 CEST 2021)
                        () -> assertTrue(
                                entityRead.contains("Project version:")
                                && entityRead.contains("Plantuml version:"),
                                m),
                        () -> {
                            final Predicate<String> pred1 = (s) -> s.contains("Project version:")
                            && s.contains("Plantuml version:");
                            assertTrue(pred1.test(entityRead), m);
                        });
            }
        }
        assertTrue(resteasyClient.isClosed());
    }

}
