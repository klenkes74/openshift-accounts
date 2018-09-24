/*
 *    Copyright 2018 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.kaiserpfalzedv.openshift.accounts.backend.dto;

import de.kaiserpfalzedv.openshift.accounts.backend.model.GroupBuilder;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ProjectBuilder;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class ProjectImplBuilder extends ProjectBuilder<ProjectImpl> {
    @Override
    public ProjectImpl build() {
        return new ProjectImpl(
                id, version,
                created, modified,
                convertCluster(),
                name,
                convertOwner()
        );
    }


    private ClusterImpl convertCluster() {
        return new ClusterImplBuilder().setCluster(cluster).build();
    }

    private AccountImpl convertOwner() {
        return new AccountImplBuilder().setOwner(owner).build();
    }
}
