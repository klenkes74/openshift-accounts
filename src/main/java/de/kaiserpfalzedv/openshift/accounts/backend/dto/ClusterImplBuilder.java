/*
 * Copyright (c) 2018 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This file is part of openshift-accounts.
 *
 * openshift-accounts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
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
