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

package de.kaiserpfalzedv.openshift.accounts.backend.model;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.BaseEntityBuilder;
import javafx.util.Builder;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class ProjectBuilder<T extends Project> extends BaseEntityBuilder<T> implements Builder<T> {
    protected Cluster cluster;
    protected String name;
    protected Account owner;

    protected final Set<Group> viewers = new HashSet<>();
    protected final Set<Group> editors = new HashSet<>();
    protected final Set<Group> admins = new HashSet<>();


    public ProjectBuilder<T> setProject(@NotNull final Project project) {
        setIdentity(project);
        setChangeable(project);
        setVersionable(project);

        setId(project.getId());
        setVersion(project.getVersion());
        setTenant(project.getTenant());

        setCreated(project.getCreated());
        setModified(project.getModified());

        setCluster(project.getCluster());
        setName(project.getName());
        setOwner(project.getOwner());

        return this;
    }


    public ProjectBuilder<T> setCluster(@NotNull final Cluster cluster) {
        this.cluster = cluster;
        return this;
    }

    public ProjectBuilder<T> setName(@NotNull final String name) {
        this.name = name;
        return this;
    }

    public ProjectBuilder<T> setOwner(@NotNull final Account owner) {
        this.owner = owner;
        return this;
    }

    public ProjectBuilder<T> setViewers(@NotNull final Collection<? extends Group> groups) {
        this.viewers.clear();
        this.viewers.addAll(groups);
        return this;
    }

    public ProjectBuilder<T> setEditors(@NotNull final Collection<? extends Group> groups) {
        this.editors.clear();
        this.editors.addAll(groups);
        return this;
    }

    public ProjectBuilder<T> setAdmins(@NotNull final Collection<? extends Group> groups) {
        this.admins.clear();
        this.admins.addAll(groups);
        return this;
    }
}
