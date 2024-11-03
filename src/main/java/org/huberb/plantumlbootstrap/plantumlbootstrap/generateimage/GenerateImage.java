/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.plantumlbootstrap.plantumlbootstrap.generateimage;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.plantuml.FileFormat;
import org.huberb.plantumlbootstrap.plantumlbootstrap.encoderdecoder.EncoderDecoder;
import org.huberb.plantumlbootstrap.plantumlbootstrap.encoderdecoder.PumlDefaultEncodedDecoded;
import org.huberb.plantumlbootstrap.plantumlbootstrap.support.StringUtility;

/**
 * Generate an image from encoded or decoded uml text.
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
     * @param decodedUmlText plantuml decoded (plain) text
     * @param req
     * @param resp
     */
    public void generateImgageFromDecodedAndSend(FileFormat fileFormat,
            String decodedUmlText,
            HttpServletRequest req, HttpServletResponse resp) {

        //---
        // extract encoded or decoded uml text
        final String decoded = (StringUtility.isStringNotEmpty(decodedUmlText))
                ? decodedUmlText
                : this.pumlDefaultEncodedDecoded.retrievePumlDefaultDecoded();
        // extract and convert file format     
        generateImageAndSend(req, resp, decoded, 0, fileFormat);
    }

    /**
     * Generate image and send image as http-response.
     *
     * @param fileFormat specifies the response format like PNG, SVG, etc.
     * @param encodedUmlText plantuml encoded text
     * @param req
     * @param resp
     */
    public void generateImageFromEncodedAndSend(FileFormat fileFormat,
            String encodedUmlText,
            HttpServletRequest req, HttpServletResponse resp) {

        //---
        // extract encoded or decoded uml text
        final String decoded = (StringUtility.isStringNotEmpty(encodedUmlText))
                ? this.encoderDecoder.decode(encodedUmlText)
                : this.pumlDefaultEncodedDecoded.retrievePumlDefaultDecoded();
        // extract and convert file format     
        generateImageAndSend(req, resp, decoded, 0, fileFormat);
    }

    /**
     * Generate image from uml text, and sent it as http-response.
     *
     * @param request
     * @param response
     * @param decodedUmlText plain plantuml text
     * @param idx
     * @param fileFormat specifies the response format like PNG, SVG, etc.
     */
    void generateImageAndSend(HttpServletRequest request,
            HttpServletResponse response,
            String decodedUmlText,
            int idx,
            FileFormat fileFormat) {
        try {
            final PublicDiagramResponse diagramResponse = new PublicDiagramResponse(request, response, fileFormat);
            diagramResponse.sendDiagram(decodedUmlText, idx);
        } catch (IOException ioex) {
            throw new GenerateImageRuntimeException("generateAndSendImage", ioex);
        }
    }

}
