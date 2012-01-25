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
import org.fusesource.fabric.api.Agent;
import org.fusesource.fabric.commands.support.FabricCommand;
import org.fusesource.fabric.zookeeper.ZkDefs;

import java.io.PrintStream;

@Command(name = "agent-list", scope = "fabric", description = "List existing agents")
public class AgentList extends FabricCommand {

    @Override
    protected Object doExecute() throws Exception {
        Agent[] agents = fabricService.getAgents();
        printAgents(agents, System.out);
        return null;
    }

    protected String getProvisionedStatus(Agent agent) {
        String provisioned = agent.getProvisionResult();
        String result = "not provisioned";

        if (provisioned != null) {
            result = provisioned;
            if (result.equals(ZkDefs.ERROR) && agent.getProvisionException() != null) {
                result += " - " + agent.getProvisionException().split(System.getProperty("line.separator"))[0];
            }
        }

        return result;
    }

    protected void printAgents(Agent[] agents, PrintStream out) {
        out.println(String.format("%-30s %-10s %-30s %-100s", "[id]", "[alive]", "[profiles]", "[provision status]"));
        for (Agent agent : agents) {
            if (agent.isRoot()) {
                out.println(String.format("%-30s %-10s %-30s %-100s", agent.getId(), agent.isAlive(), toString(agent.getProfiles()), getProvisionedStatus(agent)));
                for (Agent child : agents) {
                    if (child.getParent() == agent) {
                        out.println(String.format("%-30s %-10s %-30s %-100s", "  " + child.getId(), child.isAlive(), toString(child.getProfiles()), getProvisionedStatus(child)));
                    }
                }
            }
        }
    }

}
