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
package org.huberb.plantumlbootstrap.plantumlbootstrap.generateimage;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import net.sourceforge.plantuml.FileFormat;
import org.huberb.plantumlbootstrap.plantumlbootstrap.support.PumlDefaultEncodedDecoded;

/**
 * REST Web Service
 *
 * @author berni3
 */
@Path("generateImage")
@RequestScoped
public class GenerateImageResource {

    @Context
    private UriInfo context;

    @Inject
    private GenerateImage generateImage;

    @Inject
    private PumlDefaultEncodedDecoded pumlDefaultEncodedDecoded;

    /**
     * Creates a new instance of GenericResource
     */
    public GenerateImageResource() {
    }

    @GET
    @Path(value = "/png/encoded")
    @Produces("image/png")
    public void generateFromEncodedPngImage(
            @Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse,
            @QueryParam("text") String text) {
        final FileFormat fileFormat = FileFormat.PNG;
        final String umlTextEncoded = encodedTextOrDefault(text);
        generateImage.generateImageFromEncodedAndSend(fileFormat, umlTextEncoded, httpServletRequest, httpServletResponse);
    }

    @POST
    @Path(value = "/png/decoded")
    @Consumes(value = "text/plain")
    @Produces("image/png")
    public void generateFromDecodedPngImage(
            @Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse,
            String text) {
        final FileFormat fileFormat = FileFormat.PNG;
        final String umlTextDecoded = decodedTextOrDefault(text);
        generateImage.generateImgageFromDecodedAndSend(fileFormat, umlTextDecoded, httpServletRequest, httpServletResponse);
    }

    //-------------------------------------------------------------------------
    @GET
    @Path(value = "/svg/encoded")
    @Produces("image/svg")
    public void generateFromEncodedSvgImage(
            @Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse,
            @QueryParam("text") String text) {
        final FileFormat fileFormat = FileFormat.SVG;
        final String umlTextEncoded = encodedTextOrDefault(text);
        generateImage.generateImageFromEncodedAndSend(fileFormat, umlTextEncoded, httpServletRequest, httpServletResponse);
    }

    @POST
    @Path(value = "/svg/decoded")
    @Consumes(value = "text/plain")
    @Produces("image/svg")
    public void generateFromDecodedSvgImage(
            @Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse,
            String text) {
        final FileFormat fileFormat = FileFormat.SVG;
        final String umlTextDecoded = decodedTextOrDefault(text);
        generateImage.generateImgageFromDecodedAndSend(fileFormat, umlTextDecoded, httpServletRequest, httpServletResponse);
    }

    //-----------------------------------------------------------------------------
    String encodedTextOrDefault(String text) {
        final String umlTextEncoded;
        if (isStringEmpty(text)) {
            final String umlDefault = this.pumlDefaultEncodedDecoded.retrievePumlDefaultEncoded();
            umlTextEncoded = umlDefault;
        } else {
            umlTextEncoded = text;
        }
        return umlTextEncoded;
    }

    String decodedTextOrDefault(String text) {
        final String umlTextDecoded;
        if (isStringEmpty(text)) {
            final String umlDefault = this.pumlDefaultEncodedDecoded.retrievePumlDefaultDecoded();
            umlTextDecoded = umlDefault;
        } else {
            umlTextDecoded = text;
        }
        return umlTextDecoded;
    }

    boolean isStringEmpty(String s) {
        boolean result = false;
        result = result || s == null;
        result = result || s.isEmpty();
        return result;
    }
}
