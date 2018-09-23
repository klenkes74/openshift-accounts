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
