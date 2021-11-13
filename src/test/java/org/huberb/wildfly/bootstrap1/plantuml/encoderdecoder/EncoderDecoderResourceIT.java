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
package org.huberb.wildfly.bootstrap1.plantuml.encoderdecoder;

import java.util.function.Predicate;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class EncoderDecoderResourceIT {

    String urlBase = "http://localhost:8081/plantumlbootstrap-1.0-SNAPSHOT";

    @Test
    public void testEncode() {
        final String url = urlBase + "/webresources/encoderDecoder/encode";
        final String body = "@startuml\n"
                + "Alice --> Bob : hello\n"
                + "@enduml";

        final ResteasyClient resteasyClient = new ResteasyClientBuilder()
                .build();
        try (ResteasyClientAutoCloseable rcac = new ResteasyClientAutoCloseable(resteasyClient)) {
            final ResteasyWebTarget resteasyWebTarget = resteasyClient.target(UriBuilder.fromPath(url));
            // POST
            try (final Response encodePostResponse = resteasyWebTarget
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(body, MediaType.TEXT_PLAIN))) {
                final String entityRead = encodePostResponse.readEntity(String.class);
                System.out.printf("testEncode:%n"
                        + "HTTP code: %d%n"
                        + "HTTP content-length: %d%n"
                        + "has entity: %s%n"
                        + "entity1: %s%n"
                        + "entity2: %s%n",
                        encodePostResponse.getStatus(),
                        encodePostResponse.getLength(),
                        "?X?", //encodePostResponse.hasEntity(),
                        "?X?", //encodePostResponse.getEntity(),
                        entityRead
                );
                final String m = "" + encodePostResponse;

                assertEquals(Status.OK.getStatusCode(), encodePostResponse.getStatus(), m);

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

    @Test
    public void testDecode() {
        final String url = urlBase + "/webresources/encoderDecoder/decode";
        final String body = "AyaioKbL24ujB4tDImOoyZ8B2b9B50ovdFAJ57Jj51npCe72LWePgJav-L0U5uG2oe8KmUH0R000";

        final ResteasyClient resteasyClient = new ResteasyClientBuilder()
                .build();
        try (ResteasyClientAutoCloseable rcac = new ResteasyClientAutoCloseable(resteasyClient)) {
            final ResteasyWebTarget resteasyWebTarget = resteasyClient.target(UriBuilder.fromPath(url));
            // POST
            try (final Response decodePostResponse = resteasyWebTarget
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(body, MediaType.TEXT_PLAIN))) {
                final String entityRead = decodePostResponse.readEntity(String.class);
                System.out.printf("testDecode:%n"
                        + "HTTP code: %d%n"
                        + "HTTP content-length: %d%n"
                        + "has entity: %s%n"
                        + "entity1: %s%n"
                        + "entity2: %s%n",
                        decodePostResponse.getStatus(),
                        decodePostResponse.getLength(),
                        "?X?", //encodePostResponse.hasEntity(),
                        "?X?", //encodePostResponse.getEntity(),
                        entityRead
                );
                final String m = "" + decodePostResponse;
                assertEquals(Status.OK.getStatusCode(), decodePostResponse.getStatus(), m);

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

    static class ResteasyClientAutoCloseable implements AutoCloseable {

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
