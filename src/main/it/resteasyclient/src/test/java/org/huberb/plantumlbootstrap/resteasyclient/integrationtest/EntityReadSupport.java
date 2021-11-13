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

import javax.ws.rs.core.Response;

/**
 *
 * @author berni3
 */
public class EntityReadSupport {

    public static String retrieveEntityReadFromResponse(String m, Response response) {
        final String entityRead = response.readEntity(String.class);
        System.out.printf("%s:%n"
                + "HTTP code: %d%n"
                + "HTTP content-length: %d%n"
                + "has entity: %s%n"
                + "entity1: %s%n"
                + "entity2: %s%n", m,
                response.getStatus(),
                response.getLength(),
                "?X?", //encodePostResponse.hasEntity(),
                "?X?", //encodePostResponse.getEntity(),
                entityRead);
        return entityRead;
    }

}
