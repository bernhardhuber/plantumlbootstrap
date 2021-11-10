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

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class ApplAndPlantumlVersionTest {

    public ApplAndPlantumlVersionTest() {
    }

    /*
org.opentest4j.AssertionFailedError: expected: <> but was: 
	at org.huberb.wildfly.bootstrap1.plantuml.version.ApplAndPlantumlVersionTest.testRetrievePlantumlFullDescription(ApplAndPlantumlVersionTest.java:51)

testRetrieveProjectFullDescription  Time elapsed: 0 s  <<< FAILURE!
org.opentest4j.AssertionFailedError: expected: <> but was: <-unknown->

     */
    /**
     * Test of retrieveProjectFullDescription method, of class
     * ApplAndPlantumlVersion.
     */
    @Test
    public void testRetrieveProjectFullDescription() {
        ApplAndPlantumlVersion instance = new ApplAndPlantumlVersion();
        String result = instance.retrieveProjectFullDescription();
        // bootstrap1-1.0-SNAPSHOT
        String m = "" + result;
        assertTrue(result != null && !result.isEmpty(), m);
    }

    /**
     * Test of retrievePlantumlFullDescription method, of class
     * ApplAndPlantumlVersion.
     */
    @Test
    public void testRetrievePlantumlFullDescription() {
        ApplAndPlantumlVersion instance = new ApplAndPlantumlVersion();
        final String result = instance.retrievePlantumlFullDescription();
        // PlantUML version 1.2021.12 (Tue Oct 05 18:01:58 CEST 2021)
        String m = "" + result;
        assertTrue(result.startsWith("PlantUML version"), m);
    }

}
