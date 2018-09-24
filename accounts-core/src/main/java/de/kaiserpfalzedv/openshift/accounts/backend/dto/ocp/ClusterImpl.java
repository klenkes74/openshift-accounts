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

package de.kaiserpfalzedv.openshift.accounts.backend.dto.ocp;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.dto.base.BaseEntityImpl;
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
public class ClusterImpl extends BaseEntityImpl implements Cluster {
    private String name;

    private Set<ProjectImpl> projects = new HashSet<>();

    /**
     * @deprecated Only for JPA ...
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    protected ClusterImpl() {}

    @SuppressWarnings("WeakerAccess")
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

    @SuppressWarnings("unused")
    public ClusterImpl(@NotNull final Cluster orig) {
        this(orig.getId(), orig.getVersion(),
             orig.getCreated(), orig.getModified(),
             orig.getName(), convertProjects(orig.getProjects()));
    }

    private static Set<ProjectImpl> convertProjects(@NotNull final Collection<? extends Project> origs) {
        HashSet<ProjectImpl> result = new HashSet<>(origs.size());

        origs.forEach(p -> result.add(convertProject(p)));

        return result;
    }

    private static ProjectImpl convertProject(@NotNull final Project orig) {
        return (orig instanceof ProjectImpl ? (ProjectImpl) orig : new ProjectImpl(orig));
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
