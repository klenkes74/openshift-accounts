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
public abstract class AccountBuilder<T extends Account> extends BaseEntityBuilder<T> implements Builder<T> {
    protected String name;
    protected Person owner;
    protected final Set<Project> projects = new HashSet<>();
    protected final Set<Group> groups = new HashSet<>();

    public AccountBuilder<T> setOwner(@NotNull final Account owner) {
        setIdentity(owner);
        setVersionable(owner);
        setChangeable(owner);

        setCreated(owner.getCreated());
        setModified(owner.getModified());

        setName(owner.getName());
        setGroup(owner.getGroups());
        setProject(owner.getProjects());

        return this;
    }

    public AccountBuilder<T> setName(@NotNull final String name) {
        this.name = name;
        return this;
    }

    public AccountBuilder<T> setOwner(@NotNull final Person owner) {
        this.owner = owner;
        return this;
    }


    public AccountBuilder<T> setProject(@NotNull final Collection<? extends Project> projects) {
        this.projects.clear();
        this.projects.addAll(projects);
        return this;
    }

    public AccountBuilder<T> setGroup(@NotNull final Collection<? extends Group> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
        return this;
    }
}
