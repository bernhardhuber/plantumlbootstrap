/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.wildfly.bootstrap1.plantuml.encoderdecoder;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import net.sourceforge.plantuml.code.NoPlantumlCompressionException;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;

/**
 * Use plantuml {@link Transcoder} to encode or a decode an uml text.
 *
 * @author berni3
 */
@RequestScoped
public class EncoderDecoder {

    public static class EncodeDecoderException extends RuntimeException {

        public EncodeDecoderException(String msg, Throwable t) {
            super(msg, t);
        }

    }

    /**
     * Encode a plain plantuml text.
     *
     * @param decodedText
     * @return
     * @throws EncodeDecoderException
     */
    public String encode(String decodedText) {
        try {
            String encoded = getTranscoder().encode(decodedText);
            return encoded;
        } catch (IOException ex) {
            throw new EncodeDecoderException("Failed decodingText: " + decodedText, ex);
        }
    }

    /**
     * Decode an encoded plantuml text.
     *
     * @param encodedText
     * @return
     * @throws EncodeDecoderException
     */
    public String decode(String encodedText) {
        try {
            String decoded = getTranscoder().decode(encodedText);
            return decoded;
        } catch (NoPlantumlCompressionException ex) {
            throw new EncodeDecoderException("Failed decoding: " + encodedText, ex);
        }
    }

    Transcoder getTranscoder() {
        return TranscoderUtil.getDefaultTranscoder();
    }

}
