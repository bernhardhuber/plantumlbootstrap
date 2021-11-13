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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.plantuml.FileFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author berni3
 */
@ExtendWith(MockitoExtension.class)
public class PublicDiagramResponseTest {

    public PublicDiagramResponseTest() {
    }

    /**
     * Test of sendDiagram method, of class PublicDiagramResponse.
     */
    @Test
    public void testSendDiagram_PNG() throws IOException {
        final String uml = "@startuml\n"
                + "Alice --> Bob: hello\n"
                + "@enduml";

        int idx = 0;

        final FileFormat fileFormat = FileFormat.PNG;
        final MockHttpServletResponseFactory f = new MockHttpServletResponseFactory();
        final HttpServletRequest request = f.createHttpServletRequest();
        final HttpServletResponse response = f.createMockHttpServletResponseWithOutpuStream();
        final DelegatingServletOutputStream myServletOutputStream = f.getDelegatingServletOutputStream();

        final PublicDiagramResponse instance = new PublicDiagramResponse(request, response, fileFormat);
        instance.sendDiagram(uml, idx);

        try (final ByteArrayOutputStream baos = myServletOutputStream.getBaos()) {
            assertNotNull(baos);
            final byte[] baosByteArray = baos.toByteArray();
            assertTrue(baosByteArray.length > 0);

            final String firstFourBytesFormatted = String.format("%x %c %c %c",
                    baosByteArray[0],
                    (char) baosByteArray[1],
                    (char) baosByteArray[2],
                    (char) baosByteArray[3]
            );
            assertEquals("89 P N G", firstFourBytesFormatted);
        }
        Mockito.verify(response, Mockito.times(0)).setStatus(500);
    }

    /**
     * Test of sendCheck method, of class PublicDiagramResponse.
     */
    @Test
    public void testSendCheck() throws IOException {
        final String uml = "@startuml\n"
                + "Alice --> Bob: hello\n"
                + "@enduml";

        final FileFormat fileFormat = FileFormat.PNG;
        final MockHttpServletResponseFactory f = new MockHttpServletResponseFactory();
        final HttpServletRequest request = f.createHttpServletRequest();
        final HttpServletResponse response = f.createMockHttpServletResponseWithPrintWriter();

        final PublicDiagramResponse instance = new PublicDiagramResponse(request, response, fileFormat);
        instance.sendCheck(uml);

        String s = f.getStringWriter().toString();
        assertEquals("(2 participants)", s);

        Mockito.verify(response, Mockito.times(0)).setStatus(500);
    }

    static class MockHttpServletResponseFactory {

        private final DelegatingServletOutputStream delegatingServletOutputStream;
        private final StringWriter sw;
        private final PrintWriter pw;

        public MockHttpServletResponseFactory() {
            this.delegatingServletOutputStream = new DelegatingServletOutputStream();
            this.sw = new StringWriter();
            this.pw = new PrintWriter(this.sw);
        }

        HttpServletRequest createHttpServletRequest() {
            final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
            return request;
        }

        HttpServletResponse createMockHttpServletResponseWithOutpuStream() throws IOException {
            final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
            Mockito.when(response.getOutputStream()).thenReturn(delegatingServletOutputStream);
            return response;
        }

        HttpServletResponse createMockHttpServletResponseWithPrintWriter() throws IOException {
            final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
            Mockito.when(response.getWriter()).thenReturn(pw);
            return response;
        }

        DelegatingServletOutputStream getDelegatingServletOutputStream() {
            return this.delegatingServletOutputStream;
        }

        StringWriter getStringWriter() {
            return this.sw;
        }
    }

    static class DelegatingServletOutputStream extends ServletOutputStream {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        public ByteArrayOutputStream getBaos() {
            return baos;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // ignore
        }

        @Override
        public void write(int b) throws IOException {
            baos.write(b);
        }

        @Override
        public void close() throws IOException {
            this.baos.close();
        }

        @Override
        public void flush() throws IOException {
            this.baos.flush();
        }

    }
}
