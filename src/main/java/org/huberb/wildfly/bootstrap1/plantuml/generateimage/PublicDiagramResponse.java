/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.wildfly.bootstrap1.plantuml.generateimage;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.plantuml.FileFormat;

/**
 *
 * @author berni3
 */
public class PublicDiagramResponse extends DiagramResponse {

    public PublicDiagramResponse(HttpServletResponse r, FileFormat f, HttpServletRequest rq) {
        super(r, f, rq);
    }

    @Override
    public void sendDiagram(String uml, int idx) throws IOException {
        super.sendDiagram(uml, idx);
    }

    @Override
    public void sendCheck(String uml) throws IOException {
        super.sendCheck(uml);
    }

}
