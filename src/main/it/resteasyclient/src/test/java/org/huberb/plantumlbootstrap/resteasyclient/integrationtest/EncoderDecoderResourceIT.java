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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.huberb.plantumlbootstrap.resteasyclient.integrationtest.support.ConfigurationProps;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class EncoderDecoderResourceIT {

    // TODO make it maven configurable
    static String URL_BASE = "http://localhost:8081/plantumlbootstrap-1.0-SNAPSHOT";

    @BeforeAll
    public static void setUpConfigurationProps() throws IOException {
        final ConfigurationProps configurationProps = ConfigurationPropsFactoryForPlantumlbootstrap.create();
        URL_BASE = configurationProps.getPropertyOrDefault("urlBase", URL_BASE);
        final String m = "" + URL_BASE;
        Assumptions.assumeTrue(URL_BASE.startsWith("http"), m);
    }

    //--- encode ---
    /**
     * Test method encodeGet of EncoderDecoderResource.
     */
    @Test
    public void testEncodeGet() {
        final String url = URL_BASE + "/webresources/encoderDecoder/encode";
        final String body = "@startuml\n"
                + "Alice --> Bob : hello\n"
                + "@enduml";

        final ResteasyClient resteasyClient = new ResteasyClientBuilderImpl()
                .build();
        try (final ResteasyClientAutoCloseable rcac = new ResteasyClientAutoCloseable(resteasyClient)) {
            final ResteasyWebTarget resteasyWebTarget = resteasyClient.target(UriBuilder.fromPath(url));
            // POST
            try (final Response response = resteasyWebTarget
                    .queryParam("text", body)
                    .request()
                    .accept(MediaType.TEXT_PLAIN)
                    .get()) {
                final String entityRead = EntityReadSupport.retrieveEntityReadFromResponse("testEncodeGet", response);
                final String m = "" + response;
                assertEquals(Status.OK.getStatusCode(), response.getStatus(), m);
                assertTrue(
                        entityRead.length() > 0,
                        m);
                final Predicate<String> pred1 = (s) -> s.length() > 0;
                assertTrue(pred1.test(entityRead), m);
            }
        }
        assertTrue(resteasyClient.isClosed());
    }

    /**
     * Test method encodePost of EncoderDecoderResource.
     */
    @Test
    public void testEncodePost() {
        final String url = URL_BASE + "/webresources/encoderDecoder/encode";
        final String body = "@startuml\n"
                + "Alice --> Bob : hello\n"
                + "@enduml";

        final ResteasyClient resteasyClient = new ResteasyClientBuilderImpl()
                .build();
        try (final ResteasyClientAutoCloseable rcac = new ResteasyClientAutoCloseable(resteasyClient)) {
            final ResteasyWebTarget resteasyWebTarget = resteasyClient.target(UriBuilder.fromPath(url));
            // POST
            try (final Response response = resteasyWebTarget
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(body, MediaType.TEXT_PLAIN))) {
                final String entityRead = EntityReadSupport.retrieveEntityReadFromResponse("testEncodePost", response);
                final String m = "" + response;
                assertEquals(Status.OK.getStatusCode(), response.getStatus(), m);
                assertTrue(
                        entityRead.contains("\"decoded\":")
                        && entityRead.contains("\"encoded\":"),
                        m);
                final Predicate<String> pred1 = (s)
                        -> s.contains("\"decoded\":")
                        && s.contains("\"encoded\":");
                assertTrue(pred1.test(entityRead), m);
            }
        }
        assertTrue(resteasyClient.isClosed());
    }

    //--- decode ---
    /**
     * Test method decodeGet of EncoderDecoderResource.
     */
    @Test
    public void testDecodeGet() {
        final String url = URL_BASE + "/webresources/encoderDecoder/decode";
        final String body = "AyaioKbL24ujB4tDImOoyZ8B2b9B50ovdFAJ57Jj51npCe72LWePgJav-L0U5uG2oe8KmUH0R000";

        final ResteasyClient resteasyClient = new ResteasyClientBuilderImpl()
                .build();
        try (final ResteasyClientAutoCloseable rcac = new ResteasyClientAutoCloseable(resteasyClient)) {
            final ResteasyWebTarget resteasyWebTarget = resteasyClient.target(UriBuilder.fromPath(url));
            // POST
            try (final Response response = resteasyWebTarget
                    .queryParam("text", body)
                    .request()
                    .accept(MediaType.TEXT_PLAIN)
                    .get()) {
                final String entityRead = EntityReadSupport.retrieveEntityReadFromResponse("testDecodeGet", response);
                final String m = "" + response;
                assertEquals(Status.OK.getStatusCode(), response.getStatus(), m);
                assertTrue(
                        entityRead.length() > 0,
                        m);
                final Predicate<String> pred1 = (s) -> s.length() > 0;
                assertTrue(pred1.test(entityRead), m);
            }
        }
        assertTrue(resteasyClient.isClosed());
    }

    /**
     * Test method decodePost of EncoderDecoderResource.
     */
    @Test
    public void testDecodePost() {
        final String url = URL_BASE + "/webresources/encoderDecoder/decode";
        final String body = "AyaioKbL24ujB4tDImOoyZ8B2b9B50ovdFAJ57Jj51npCe72LWePgJav-L0U5uG2oe8KmUH0R000";

        final ResteasyClient resteasyClient = new ResteasyClientBuilderImpl()
                .build();
        try (final ResteasyClientAutoCloseable rcac = new ResteasyClientAutoCloseable(resteasyClient)) {
            final ResteasyWebTarget resteasyWebTarget = resteasyClient.target(UriBuilder.fromPath(url));
            // POST
            try (final Response response = resteasyWebTarget
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(body, MediaType.TEXT_PLAIN))) {
                final String entityRead = EntityReadSupport.retrieveEntityReadFromResponse("testDecodePost", response);
                final String m = "" + response;
                assertEquals(Status.OK.getStatusCode(), response.getStatus(), m);
                assertTrue(
                        entityRead.contains("\"decoded\":")
                        && entityRead.contains("\"encoded\":"),
                        m);
                final Predicate<String> pred1 = (s)
                        -> s.contains("\"decoded\":")
                        && s.contains("\"encoded\":");
                assertTrue(pred1.test(entityRead), m);
            }
        }
        assertTrue(resteasyClient.isClosed());
    }

    public static class ResteasyClientAutoCloseable implements AutoCloseable {

        private final ResteasyClient client;

        public ResteasyClientAutoCloseable(ResteasyClient client) {
            this.client = client;
        }

        public ResteasyClient client() {
            return this.client;
        }

        @Override
        public void close() {
            if (client != null && !client.isClosed()) {
                client.close();
            }
        }
    }
}
