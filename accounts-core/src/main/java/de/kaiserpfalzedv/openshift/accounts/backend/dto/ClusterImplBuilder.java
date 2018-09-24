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

import de.kaiserpfalzedv.openshift.accounts.backend.model.AccountBuilder;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ClusterBuilder;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class ClusterImplBuilder extends ClusterBuilder<ClusterImpl> {
    @Override
    public ClusterImpl build() {
        return new ClusterImpl(
                id, version,
                created, modified,
                name, convertProjects()
        );
    }

    private Set<? extends ProjectImpl> convertProjects() {
        HashSet<ProjectImpl> result = new HashSet<>(projects.size());

        projects.forEach(p -> result.add(convertProject(p)));

        return result;
    }

    private ProjectImpl convertProject(@NotNull final Project project) {
        if (ProjectImpl.class.isAssignableFrom(project.getClass())) {
            return (ProjectImpl) project;
        } else {
            return new ProjectImplBuilder().setProject(project).build();
        }
    }
}
