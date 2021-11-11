/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.wildfly.bootstrap1.plantuml.generateimage;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.plantuml.FileFormat;
import org.huberb.wildfly.bootstrap1.plantuml.encoderdecoder.EncoderDecoder;
import org.huberb.wildfly.bootstrap1.plantuml.support.PumlDefaultEncodedDecoded;

/**
 *
 * @author berni3
 */
@RequestScoped
public class GenerateImage {

    @Inject
    private PumlDefaultEncodedDecoded pumlDefaultEncodedDecoded;
    @Inject
    private EncoderDecoder encoderDecoder;

    public static class GenerateImageRuntimeException extends RuntimeException {

        public GenerateImageRuntimeException(String string, Throwable thrwbl) {
            super(string, thrwbl);
        }
    }

    /**
     * Generate image and send image as http-response.
     *
     * @param fileFormat specifies the response format like PNG, SVG, etc.
     * @param optDecoded plantuml decoded (plain) text
     * @param req
     * @param resp
     */
    public void generateImgageFromDecodedAndSend(FileFormat fileFormat,
            String optDecoded,
            HttpServletRequest req, HttpServletResponse resp) {

        //---
        // extract encoded or decoded uml text
        final String decoded;
        {
            if (StringUtility.isStringNotEmpty(optDecoded)) {
                decoded = optDecoded;
            } else {
                final String umlDefault = this.pumlDefaultEncodedDecoded.retrievePumlDefaultDecoded();
                decoded = umlDefault;
            }
        }
        // extract and convert file format     
        generateImageAndSend(req, resp, decoded, 0, fileFormat);
    }

    /**
     * Generate image and send image as http-response.
     *
     * @param fileFormat specifies the response format like PNG, SVG, etc.
     * @param optEncoded plantuml encoded text
     * @param req
     * @param resp
     */
    public void generateImageFromEncodedAndSend(FileFormat fileFormat,
            String optEncoded,
            HttpServletRequest req, HttpServletResponse resp) {

        //---
        // extract encoded or decoded uml text
        final String decoded;
        {
            if (StringUtility.isStringNotEmpty(optEncoded)) {
                decoded = this.encoderDecoder.decode(optEncoded);
            } else {
                final String umlDefault = this.pumlDefaultEncodedDecoded.retrievePumlDefaultDecoded();
                decoded = umlDefault;
            }
        }
        // extract and convert file format     
        generateImageAndSend(req, resp, decoded, 0, fileFormat);
    }

    /**
     * Generate image from uml text, and sent it as http-response.
     *
     * @param request
     * @param response
     * @param uml plain plantuml text
     * @param idx
     * @param fileFormat specifies the response format like PNG, SVG, etc.
     */
    void generateImageAndSend(HttpServletRequest request,
            HttpServletResponse response,
            String uml,
            int idx,
            FileFormat fileFormat) {
        try {
            final PublicDiagramResponse diagramResponse = new PublicDiagramResponse(request, response, fileFormat);
            diagramResponse.sendDiagram(uml, idx);
        } catch (IOException ioex) {
            throw new GenerateImageRuntimeException("generateAndSendImage", ioex);
        }
    }

    public static class StringUtility {

        public static boolean isStringNotEmpty(String s) {
            return !isStringEmpty(s);
        }

        public static boolean isStringEmpty(String s) {
            boolean result = false;
            result = result || s == null;
            result = result || s.isEmpty();
            return result;
        }
    }

}
