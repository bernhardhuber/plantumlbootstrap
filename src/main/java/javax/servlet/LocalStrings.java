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
package javax.servlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Need this class as SerlvetOuputStream implementation references resource
 * bundle javax.servlet.LoclaStrings.
 *
 * @author berni3
 */
public class LocalStrings extends ResourceBundle {

    @Override
    protected Object handleGetObject(String key) {
        return this.getClass().getName() + ":" + key + ":" + "no message value, yet";
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.emptyEnumeration();
    }

}
