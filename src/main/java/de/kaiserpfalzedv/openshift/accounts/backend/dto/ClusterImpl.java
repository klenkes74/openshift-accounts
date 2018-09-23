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

import de.kaiserpfalzedv.openshift.accounts.backend.dto.base.BaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Cluster;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Entity(name = "Clusters")
@Table(
        name = "CLUSTERS"
)
public class ClusterImpl extends BaseEntity implements Cluster {
    private String name;

    private Set<ProjectImpl> projects = new HashSet<>();

    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public ClusterImpl() {}

    public ClusterImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final Collection<? extends ProjectImpl> projects
    ) {
        super(id, version, Project.DEFAULT_TENANT, modified, created);

        setName(name);
        setProjects(projects);
    }


    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }


    public Set<ProjectImpl> getProjects() {
        return projects;
    }

    public void setProjects(@NotNull final Collection<? extends ProjectImpl> projects) {
        this.projects.clear();

        this.projects.addAll(projects);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                ClusterImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .toString();
    }
}
