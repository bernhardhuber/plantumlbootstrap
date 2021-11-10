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
package org.huberb.wildfly.bootstrap1.plantuml.version;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author berni3
 */
@Path("applAndPlantumlVersion")
@RequestScoped
public class ApplAndPlantumlVersionResource {

    @Context
    private UriInfo context;

    @Inject
    private ApplAndPlantumlVersion applAndPlantumlVersion;

    @GET
    @Path(value = "/applAndPlantumlVersion")
    @Produces(MediaType.TEXT_PLAIN)
    public String applAndPlantumlVersion() {
        String result = String.format(
                "Project version: %s\n"
                + "Plantuml version: %s\n",
                this.applAndPlantumlVersion.retrieveProjectFullDescription(),
                this.applAndPlantumlVersion.retrievePlantumlFullDescription()
        );
        return result;
    }
}
