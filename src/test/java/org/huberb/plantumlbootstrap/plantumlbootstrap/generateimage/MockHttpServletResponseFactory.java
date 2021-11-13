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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mockito.Mockito;

/**
 * Helper class creating {@link HttpServletRequest}, and
 * {@link HttpServletResponse} objects.
 */
class MockHttpServletResponseFactory {

    private final PublicDiagramResponseTest.DelegatingServletOutputStream delegatingServletOutputStream;
    private final StringWriter sw;
    private final PrintWriter pw;

    public MockHttpServletResponseFactory() {
        this.delegatingServletOutputStream = new PublicDiagramResponseTest.DelegatingServletOutputStream();
        this.sw = new StringWriter();
        this.pw = new PrintWriter(this.sw);
    }

    HttpServletRequest createHttpServletRequest() {
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        return request;
    }

    /**
     * Create mocking {@link  HttpServletResponse} assuming
     * {@link HttpServletResponse#getOutputStream} will be invoked.
     *
     * @return
     * @throws IOException
     * @see
     * MockHttpServletResponseFactory#getDelegatingServletOutputStream()
     */
    HttpServletResponse createMockHttpServletResponseWithOutpuStream() throws IOException {
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getOutputStream()).thenReturn(delegatingServletOutputStream);
        return response;
    }

    /**
     * Create mocking {@link  HttpServletResponse} assuming
     * {@link HttpServletResponse#getWriter} will be invoked.
     *
     * @return
     * @throws IOException
     * @see MockHttpServletResponseFactory#getStringWriter()
     */
    HttpServletResponse createMockHttpServletResponseWithPrintWriter() throws IOException {
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getWriter()).thenReturn(pw);
        return response;
    }

    PublicDiagramResponseTest.DelegatingServletOutputStream getDelegatingServletOutputStream() {
        return this.delegatingServletOutputStream;
    }

    StringWriter getStringWriter() {
        return this.sw;
    }
    
}
