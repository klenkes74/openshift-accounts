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
import de.kaiserpfalzedv.openshift.accounts.backend.model.Account;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Person;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class AccountImpl extends BaseEntity implements Account {
    private String name;

    private PersonImpl owner;

    private final Set<ProjectImpl> projects = new HashSet<>();

    private final Set<GroupImpl> groups = new HashSet<>();

    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public AccountImpl() {}


    /**
     * Created the new account.
     * @param id The ID of the account.
     * @param version The version of this account entry.
     * @param name The name of this account.
     * @param owner The owner of the account.
     */
    @SuppressWarnings("unused")
    public AccountImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final PersonImpl owner,
            @NotNull final Collection<? extends ProjectImpl> projects,
            @NotNull final Collection<? extends GroupImpl> groups
    ) {
        super(id, version, Project.DEFAULT_TENANT, created, modified);

        setName(name);
        setOwner(owner);

        setProjects(projects);
        setGroups(groups);
    }


    public String getName() {
        return name;
    }

    private void setName(@NotNull final String name) {
        this.name = name;
    }


    public Person getOwner() {
        return owner;
    }

    private void setOwner(@NotNull final PersonImpl person) {
        this.owner = person;
    }


    public Set<? extends Group> getGroups() {
        return groups;
    }

    private void setGroups(@NotNull final Collection<? extends GroupImpl> groups) {
        this.groups.clear();

        this.groups.addAll(groups);
    }


    public Set<? extends Project> getProjects() {
        return projects;
    }

    private void setProjects(@NotNull final Collection<? extends ProjectImpl> projects) {
        this.projects.clear();

        this.projects.addAll(projects);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                AccountImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("owner='" + owner + "'")
                .add("projectsOwned=" + projects.size())
                .add("groupsOwned=" + groups.size())
                .toString();
    }
}
