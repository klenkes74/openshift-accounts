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

package de.kaiserpfalzedv.openshift.accounts.backend.jpa;

import de.kaiserpfalzedv.openshift.accounts.backend.model.Cluster;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;

import javax.persistence.*;
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
public class JPACluster extends JPABaseEntity {
    @Column(name = "NAME_", length = 100, nullable = false)
    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "CLUSTERS_PROJECTS",
            joinColumns = { @JoinColumn(name = "CLUSTER_", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "PROJECT_", nullable = false) }
    )
    private Set<JPAProject> projects = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public JPACluster() {}

    public JPACluster(@NotNull final String name) {
        this.name = name;
    }

    public JPACluster(@NotNull final String name, @NotNull final Collection<? extends JPAProject> projects) {
        setName(name);
        setProjects(projects);
    }

    public JPACluster(
            @NotNull final UUID id,
            @NotNull final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final Collection<? extends JPAProject> projects
    ) {
        super(id, version, created, modified);

        setName(name);
        setProjects(projects);
    }

    public JPACluster(@NotNull final Cluster orig) {
        this(orig.getId(), orig.getVersion(), orig.getCreated(), orig.getModified(),
                orig.getName(), convertProjects(orig.getProjects()));
    }

    private static Collection<? extends JPAProject> convertProjects(@NotNull final Collection<? extends Project> projects) {
        HashSet<JPAProject> result = new HashSet<>(projects.size());

        projects.forEach(p -> result.add(convertProject(p)));

        return result;
    }

    private static JPAProject convertProject(@NotNull final Project orig) {
        return (orig instanceof JPAProject ? (JPAProject) orig : new JPAProject(orig));
    }


    public String getName() {
        return name;
    }

    private void setName(@NotNull final String name) {
        this.name = name;
    }


    public Set<JPAProject> getProjects() {
        return projects;
    }

    private void setProjects(@NotNull final Collection<? extends JPAProject> projects) {
        this.projects.clear();
        this.projects.addAll(projects);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                JPACluster.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("projectCount=" + projects.size())
                .toString();
    }
}
