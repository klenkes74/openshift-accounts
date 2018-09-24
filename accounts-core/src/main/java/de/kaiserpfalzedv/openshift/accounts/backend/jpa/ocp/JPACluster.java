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

package de.kaiserpfalzedv.openshift.accounts.backend.jpa.ocp;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Cluster;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Project;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Entity(name = "Clusters")
@Table(
        name = "CLUSTERS"
)
public class JPACluster extends JPABaseEntity implements Cluster {
    @Column(name = "NAME_", length = 100, nullable = false)
    private String name;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "cluster",
            orphanRemoval = true
    )
    private HashSet<JPAProject> projects = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    protected JPACluster() {}

    @SuppressWarnings("deprecation")
    public JPACluster(@NotNull final String name) {
        setName(name);
    }

    @SuppressWarnings({"unused", "deprecation"})
    public JPACluster(@NotNull final String name, @NotNull final Collection<? extends JPAProject> projects) {
        setName(name);
        setProjects(projects);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public JPACluster(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final Collection<? extends Project> projects
    ) {
        super(id, version, created, modified);

        setName(name);
        setProjects(projects);
    }

    @SuppressWarnings("WeakerAccess")
    public JPACluster(@NotNull final Cluster orig) {
        this(orig.getId(), orig.getVersion(), orig.getCreated(), orig.getModified(),
                orig.getName(), orig.getProjects());
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NotNull final String name) {
        this.name = name;
    }


    @Override
    public Set<? extends Project> getProjects() {
        return projects;
    }

    @Override
    public void setProjects(@NotNull Collection<? extends Project> projects) {
        clearProjects();
        addProjects(projects);
    }

    @Override
    public void clearProjects() {
        this.projects.clear();
    }

    @Override
    public void addProjects(@NotNull final Collection<? extends Project> projects) {
        this.projects.addAll(convertProjects(projects));
    }

    private Collection<JPAProject> convertProjects(@NotNull final Collection<? extends Project> projects) {
        HashSet<JPAProject> result = new HashSet<>(projects.size());
        projects.forEach(p -> result.add(convertProject(p)));
        return result;
    }

    private JPAProject convertProject(@NotNull final Project project) {
        return (project instanceof JPAProject ? (JPAProject) project : new JPAProject(project));
    }

    @Override
    public void addProject(@NotNull final Project project) {
        this.projects.add(convertProject(project));
    }

    @Override
    public void removeProjects(@NotNull final Collection<? extends Project> projects) {
        this.projects.removeAll(convertProjects(projects));
    }

    @Override
    public void removeProject(@NotNull final Project project) {
        this.projects.remove(convertProject(project));
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                JPACluster.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("version=" + getVersion())
                .add("created=" + getCreated())
                .add("modified=" + getModified())
                .add("name='" + getShortName() + "'")
                .add("projectCount=" + projects.size())
                .toString();
    }
}
