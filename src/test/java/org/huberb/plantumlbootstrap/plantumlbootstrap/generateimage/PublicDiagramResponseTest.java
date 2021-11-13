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
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.plantuml.FileFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
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
    public void testSendDiagram() throws Exception {
        final String decodedSample = "@startuml\n"
                + "Alice --> Bob: hello\n"
                + "@enduml";
        String uml = decodedSample;

        int idx = 0;

        final FileFormat fileFormat = FileFormat.PNG;
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        final DelegatingServletOutputStream myServletOutputStream = new DelegatingServletOutputStream();
        Mockito.when(response.getOutputStream()).thenReturn(myServletOutputStream);
        final PublicDiagramResponse instance = new PublicDiagramResponse(request, response, fileFormat);
        instance.sendDiagram(uml, idx);

        final ByteArrayOutputStream baos = myServletOutputStream.getBaos();
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

    /**
     * Test of sendCheck method, of class PublicDiagramResponse.
     */
    @Test
    @Disabled("TODO implement me")
    public void testSendCheck() throws Exception {
        System.out.println("sendCheck");
        String uml = "";
        PublicDiagramResponse instance = null;
        instance.sendCheck(uml);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
