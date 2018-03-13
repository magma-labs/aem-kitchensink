/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.cone.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import io.cone.aem.core.custom.ConfigurationFactoryService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.settings.SlingSettingsService;

import java.util.List;

@Model(adaptables=Resource.class)
public class HelloWorldModel {

    @Inject
    private SlingSettingsService settings;

    @Inject
    private List<ConfigurationFactoryService> services;

    @Inject @Named("sling:resourceType") @Default(values="No resourceType")
    protected String resourceType;

    @Inject @Optional
    private String text;

    private String message;

    @PostConstruct
    protected void init() {
        message = "\tHello World!\n";
        message += "\tThis is instance: " + settings.getSlingId() + "\n";
        message += "\tResource type is: " + resourceType + "\n";
        message += "\tThe value of the injected 'text' field is: " + text + "\n";

        for (ConfigurationFactoryService service : services) {
            message += "\tThe service " + service.hashCode() + " says: " + service.sayHello() + "\n";
        }
    }

    public String getMessage() {
        return message;
    }
}
