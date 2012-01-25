/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fusesource.fabric.internal;

import org.fusesource.fabric.api.Profile;
import org.fusesource.fabric.api.Version;
import org.fusesource.fabric.service.FabricServiceImpl;

import java.util.Arrays;

public class VersionImpl implements Version {

    private final String name;
    private final FabricServiceImpl service;

    public VersionImpl(String name, FabricServiceImpl service) {
        this.name = name;
        this.service = service;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Version getDerivedFrom() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Profile[] getProfiles() {
        return service.getProfiles(name);
    }

    @Override
    public Profile getProfile(String name) {
        return service.getProfile(this.name, name);
    }

    @Override
    public Profile createProfile(String name) {
        return service.createProfile(this.name, name);
    }

    @Override
    public void delete() {
        service.deleteVersion(name);
    }

}
