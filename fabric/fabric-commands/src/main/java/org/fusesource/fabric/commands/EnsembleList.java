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
package org.fusesource.fabric.commands;

import org.apache.felix.gogo.commands.Command;
import org.fusesource.fabric.commands.support.EnsembleCommandSupport;

import java.io.PrintStream;
import java.util.List;

@Command(name = "ensemble-list", scope = "fabric", description = "Lists the agents in the ZooKeeper ensemble", detailedDescription = "classpath:ensemble.txt")
public class EnsembleList extends EnsembleCommandSupport {

    @Override
    protected Object doExecute() throws Exception {
        PrintStream out = System.out;
        List<String> agents = service.getClusterAgents();
        if (agents != null) {
            out.println("[id]");
            for (String agent : agents) {
                out.println(agent);
            }
        }
        return null;
    }

}
