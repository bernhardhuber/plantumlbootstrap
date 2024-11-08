/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * Project Info:  http://plantuml.sourceforge.net
 *
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */
package org.huberb.plantumlbootstrap.plantumlbootstrap.generateimage;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.plantuml.BlockUml;
import net.sourceforge.plantuml.ErrorUml;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.StringUtils;
import net.sourceforge.plantuml.core.Diagram;
import net.sourceforge.plantuml.core.ImageData;
import net.sourceforge.plantuml.error.PSystemError;
import net.sourceforge.plantuml.version.Version;

/**
 * Delegates the diagram generation from the UML source and the filling of the
 * HTTP response with the diagram in the right format. Its own responsibility is
 * to produce the right HTTP headers.
 */
class DiagramResponse {

    private static final String POWERED_BY = "PlantUML Version " + Version.versionString();
    private static final Map<FileFormat, String> CONTENT_TYPE;

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final FileFormat format;

    static {
        final Map<FileFormat, String> map = new HashMap<>();
        map.put(FileFormat.PNG, "image/png");
        map.put(FileFormat.SVG, "image/svg+xml");
        map.put(FileFormat.EPS, "application/postscript");
        map.put(FileFormat.UTXT, "text/plain;charset=UTF-8");
        map.put(FileFormat.BASE64, "text/plain; charset=x-user-defined");
        CONTENT_TYPE = Collections.unmodifiableMap(map);
    }

    DiagramResponse(HttpServletRequest request, HttpServletResponse response, FileFormat fileFormat) {
        this.response = response;
        this.format = fileFormat;
        this.request = request;
    }

    /**
     * Entry point convert a plantuml text to a diagram, and sent it via
     * http-response.
     *
     * @param plantumlText
     * @param idx
     * @throws IOException
     */
    public void sendDiagram(String plantumlText, int idx) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType(getContentType());
        final SourceStringReader reader = new SourceStringReader(plantumlText);
        if (format == FileFormat.BASE64) {
            // net.sourceforge.plantuml.code.Base64Coder; not supported anymore
            //final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //final DiagramDescription result = reader.outputImage(baos, idx, new FileFormatOption(FileFormat.PNG));
            //baos.close();
            //final String encodedBytes = "data:image/png;base64,"
            //        + Base64Coder.encodeLines(baos.toByteArray()).replaceAll("\\s", "");
            //response.getOutputStream().write(encodedBytes.getBytes());
            //return;
            throw new IllegalArgumentException("FileFormat.BASE64 not supported");
        }
        final BlockUml blockUml = reader.getBlocks().get(0);
        if (notModified(blockUml)) {
            addHeaderForCache(blockUml);
            response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }
        if (StringUtils.isDiagramCacheable(plantumlText)) {
            addHeaderForCache(blockUml);
        }
        final Diagram diagram = blockUml.getDiagram();
        if (diagram instanceof PSystemError) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        final ImageData result = diagram.exportDiagram(response.getOutputStream(), idx, new FileFormatOption(format));
    }

    private boolean notModified(BlockUml blockUml) {
        final long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if (ifModifiedSince != -1 && ifModifiedSince != blockUml.lastModified()) {
            return false;
        }
        final String ifNoneMatch = request.getHeader("If-None-Match");
        if (ifNoneMatch == null) {
            return false;
        }
        final String etag = blockUml.etag();
        return ifNoneMatch.contains(etag);
    }

    private void addHeaderForCache(BlockUml blockUml) {
        long today = System.currentTimeMillis();
        // Add http headers to force the browser to cache the image
        final int maxAge = 3600 * 24 * 5;
        response.addDateHeader("Expires", today + 1000L * maxAge);
        response.addDateHeader("Date", today);

        response.addDateHeader("Last-Modified", blockUml.lastModified());
        response.addHeader("Cache-Control", "public, max-age=" + maxAge);
        response.addHeader("Etag", "\"" + blockUml.etag() + "\"");
        final Diagram diagram = blockUml.getDiagram();
        response.addHeader("X-PlantUML-Diagram-Description", diagram.getDescription().getDescription());
        if (diagram instanceof PSystemError) {
            final PSystemError error = (PSystemError) diagram;
            for (ErrorUml err : error.getErrorsUml()) {
                response.addHeader("X-PlantUML-Diagram-Error", err.getError());
                response.addHeader("X-PlantUML-Diagram-Error-Line", "" + err.getLineLocation().getPosition());
            }
        }
        addHeaders(response);
    }

    private void addHeaders(HttpServletResponse response) {
        response.addHeader("X-Powered-By", POWERED_BY);
        response.addHeader("X-Patreon", "Support us on http://plantuml.com/patreon");
        response.addHeader("X-Donate", "http://plantuml.com/paypal");
    }

    private String getContentType() {
        return CONTENT_TYPE.get(format);
    }

}
