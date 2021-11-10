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
package org.huberb.wildfly.bootstrap1.plantuml.support;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.huberb.wildfly.bootstrap1.plantuml.encoderdecoder.EncoderDecoder;

/**
 *
 * @author berni3
 */
@ApplicationScoped
public class PumlDefaultEncodedDecoded {

    private String pumlDefaultDecoded;
    private String pumlDefaultEncoded;

    @Inject
    private EncoderDecoder encoderDecoder;

    @PostConstruct
    protected void postConstruct() {
        this.pumlDefaultDecoded = String.format("@startuml%n"
                + "Alice --> Bob : hello%n"
                + "@enduml");
        this.pumlDefaultEncoded = encoderDecoder.encode(pumlDefaultDecoded);
    }

    public String retrievePumlDefaultDecoded() {
        return this.pumlDefaultDecoded;
    }

    public String retrievePumlDefaultEncoded() {
        return this.pumlDefaultEncoded;
    }
}
