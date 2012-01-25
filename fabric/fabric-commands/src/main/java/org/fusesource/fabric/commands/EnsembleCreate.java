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

import java.util.List;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.fusesource.fabric.commands.support.EnsembleCommandSupport;
import org.fusesource.fabric.zookeeper.commands.Import;

@Command(name = "ensemble-create", scope = "fabric", description = "Create a new ZooKeeper ensemble", detailedDescription = "classpath:ensemble.txt")
public class EnsembleCreate extends EnsembleCommandSupport {

    @Option(name = "--clean", description = "Clean local zookeeper cluster and configurations")
    private boolean clean;

    @Option(name = "--no-import", description = "Disable the import of the sample registry data from ")
    private boolean noImport;

    @Option(name = "--import-dir", description = "Directory of files to import into the newly created ensemble")
    private String importDir = getDefaultImportDir();

    @Option(name="-v", aliases={"--verbose"}, description="Verbose output of files being imported")
    boolean verbose = false;

    @Option(name="-t", aliases={"--time"}, description="The amount of time to wait for the ensemble to startup before trying to import the default data")
    long ensembleStartupTime = 2000L;

    @Argument(required = false, multiValued = true, description = "List of agents")
    private List<String> agents;

    @Override
    protected Object doExecute() throws Exception {
        if (clean) {
            service.clean();
        } else {
            if (agents == null || agents.isEmpty()) {
                throw new IllegalStateException("No agents specified.");
            }
        }
        if (agents != null && !agents.isEmpty()) {
            service.createCluster(agents);

            // now lets populate the registry with files from a mvn plugin
            if (!noImport) {
                // now lets sleep for a bit to give the ensemble chance to get ready :)
                if (ensembleStartupTime > 0L) {
                    try {
                        Thread.sleep(ensembleStartupTime);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }

                Import tool = new Import();
                tool.setSource(importDir);
                tool.setBundleContext(getBundleContext());
                tool.setZooKeeper(service.getZooKeeper());
                if (verbose) {
                    tool.setVerbose(verbose);
                }
                return tool.execute(session);
            }
        }
        return null;
    }

    private static String getDefaultImportDir() {
        return System.getProperty("karaf.home", ".") + "/import";
    }

}
