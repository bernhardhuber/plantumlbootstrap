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
package org.huberb.wildfly.bootstrap1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
@org.junit.jupiter.api.Disabled
public class NewEmptyJUnitTest {

    public NewEmptyJUnitTest() {
    }

    @Test
    public void succeedAssertEquals() {
        assertEquals(1, 1);
    }

    @Test
    public void succeedAssertEqualsMessage() {
        assertEquals(1, 1, "succeedMessage");
    }

    @Test
    public void failAssertEquals() {
        assertEquals(0, 1);
    }

    @Test
    public void failAssertEqualsMessage() {
        assertEquals(1, 1, "failAssertEqualsMessage");
    }

    @Test
    public void failFail() {
        fail();
    }

    @Test
    public void failFailMessage() {
        fail("failMessage");
    }

    @Test()
    @Disabled
    public void testDisabled() {
        assertEquals(0, 1);
    }

    @Test()
    @Disabled(value = "disabledMessage")
    public void testDisabledMessage() {
        assertEquals(0, 1);
    }

}
