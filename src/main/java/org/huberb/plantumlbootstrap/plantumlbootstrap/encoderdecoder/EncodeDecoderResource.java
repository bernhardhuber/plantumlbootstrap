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
package org.huberb.plantumlbootstrap.plantumlbootstrap.encoderdecoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author berni3
 */
@Path("encoderDecoder")
@RequestScoped
public class EncodeDecoderResource {

    @Context
    private UriInfo context;

    @Inject
    private EncoderDecoder encoderDecoder;
    @Inject
    private PumlDefaultEncodedDecoded pumlDefaultEncodedDecoded;

    @GET
    @Path(value = "/encode")
    @Produces(MediaType.TEXT_PLAIN)
    public Response encodeGet(@QueryParam("text") String text) {
        final String encoded = (text == null)
                ? this.pumlDefaultEncodedDecoded.retrievePumlDefaultEncoded()
                : encoderDecoder.encode(text);
        return Response.status(Status.OK)
                .entity(encoded)
                .build();
    }

    @POST
    @Path(value = "/encode")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response encodePost(String text) {
        final String encoded = (text == null)
                ? this.pumlDefaultEncodedDecoded.retrievePumlDefaultEncoded()
                : encoderDecoder.encode(text);
        return Response
                .status(Status.OK)
                .entity(new MapBuilder()
                        .keyValue("decoded", text)
                        .keyValue("encoded", encoded)
                        .build())
                .build();
    }

    //-------------------------------------------------------------------------
    @GET
    @Path(value = "/decode")
    @Produces(MediaType.TEXT_PLAIN)
    public Response decodeGet(@QueryParam("text") String text) {
        final String decoded = (text == null)
                ? this.pumlDefaultEncodedDecoded.retrievePumlDefaultDecoded()
                : encoderDecoder.decode(text);
        return Response.status(Status.OK)
                .entity(decoded)
                .build();
    }

    @POST
    @Path(value = "/decode")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response decodePost(String text) {
        final String decoded = (text == null)
                ? this.pumlDefaultEncodedDecoded.retrievePumlDefaultDecoded()
                : encoderDecoder.decode(text);
        return Response
                .status(Status.OK)
                .entity(new MapBuilder()
                        .keyValue("decoded", decoded)
                        .keyValue("encoded", text)
                        .build())
                .build();
    }

    static class MapBuilder {

        private final Map<String, Object> m = new HashMap<>();

        MapBuilder keyValue(String k, Object v) {
            m.put(k, v);
            return this;
        }

        Map<String, Object> build() {
            return Collections.unmodifiableMap(m);
        }
    }
}
