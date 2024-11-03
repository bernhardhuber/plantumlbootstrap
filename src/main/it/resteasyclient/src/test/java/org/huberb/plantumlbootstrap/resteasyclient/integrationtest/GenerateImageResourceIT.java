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

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.io.IOUtils;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author berni3
 */
public class GenerateImageResourceIT {

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
    public void testGenerateImagePngDecoded() throws IOException {
        final String url = URL_BASE + "/webresources/generateImage/png/decoded";
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
                    .accept("image/png")
                    .post(Entity.entity(body, MediaType.TEXT_PLAIN))) {
                final String m = "" + response;
                assertAll(
                        () -> assertEquals(Status.OK.getStatusCode(), response.getStatus(), m),
                        () -> assertTrue(response.hasEntity(), m)
                );

                final byte[] bytes = responseReadEntity(response);
                assertAll(
                        () -> assertNotNull(bytes),
                        () -> assertTrue(bytes.length > 0)
                );
                try (InputStream is = new ByteArrayInputStream(bytes)) {
                    assertAll(
                            () -> assertNotNull(is, m),
                            () -> {
                                final byte[] bytesOfRange_0_10 = new byte[10];
                                is.read(bytesOfRange_0_10, 0, 10);
                                final String magic = String.format("%x %c %c %c",
                                        bytesOfRange_0_10[0],
                                        bytesOfRange_0_10[1],
                                        bytesOfRange_0_10[2],
                                        bytesOfRange_0_10[3]
                                );
                                assertEquals("89 P N G", magic, m);
                            });
                }
                try (InputStream is = new ByteArrayInputStream(bytes)) {
                    assertNotNull(is, m);
                    final BufferedImage bufferedImage = ImageIO.read(is);
                    assertAll(
                            () -> assertEquals(109, bufferedImage.getWidth()),
                            () -> assertEquals(119, bufferedImage.getHeight())
                    );
                }
            }
        }
        assertTrue(resteasyClient.isClosed());
    }

    byte[] responseReadEntity(Response response) throws IOException {
        final byte[] bytes;
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream(); InputStream is = response.readEntity(InputStream.class)) {
            IOUtils.copyLarge(is, baos);
            baos.flush();
            bytes = baos.toByteArray();
        }
        return bytes;
    }
}
