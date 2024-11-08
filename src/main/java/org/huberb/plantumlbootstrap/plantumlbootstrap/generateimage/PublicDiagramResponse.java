/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.plantumlbootstrap.plantumlbootstrap.generateimage;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.plantuml.FileFormat;

/**
 * Define an API Wrapper for {@link DiagramResponse}.
 *
 * @author berni3
 */
public class PublicDiagramResponse extends DiagramResponse {

    public PublicDiagramResponse(HttpServletRequest request, HttpServletResponse response, FileFormat fileFormat) {
        super(request, response, fileFormat);
    }

    @Override
    public void sendDiagram(String uml, int idx) throws IOException {
        super.sendDiagram(uml, idx);
    }

}
