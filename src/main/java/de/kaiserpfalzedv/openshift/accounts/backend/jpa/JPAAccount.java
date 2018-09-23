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

import de.kaiserpfalzedv.openshift.accounts.backend.model.Account;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Person;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Entity(name = "Accounts")
@Table(
        name = "ACCOUNTS"
)
public class JPAAccount extends JPABaseEntity {
    @Column(name = "NAME_", nullable = false)
    private String name;

    @Column(name = "OWNER_", nullable = false)
    private JPAPerson owner;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<JPAProject> projects = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<JPAGroup> groups = new HashSet<>();

    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public JPAAccount() {}

    /**
     * Created the new account.
     * @param owner The owner of the account.
     */
    @SuppressWarnings("unused")
    public JPAAccount(
            @NotNull final UUID id,
            @NotNull final Long version,
            @NotNull final String name,
            @NotNull final JPAPerson owner
    ) {
        super(Project.DEFAULT_TENANT, id, version);

        setName(name);
        setOwner(owner);
    }

    /**
     * Created the new account.
     * @param owner The owner of the account.
     */
    @SuppressWarnings("unused")
    public JPAAccount(
            @NotNull final UUID id,
            @NotNull final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final JPAPerson owner
    ) {
        super(Project.DEFAULT_TENANT, id, version, created, modified);

        setName(name);
        setOwner(owner);
    }

    public JPAAccount(@NotNull final Account account) {
        this(account.getId(), account.getVersion(),
                account.getCreated(), account.getModified(),
                account.getName(), new JPAPerson(account.getOwner()));
    }


    public String getName() {
        return name;
    }

    private void setName(@NotNull final String name) {
        this.name = name;
    }


    public JPAPerson getOwner() {
        return owner;
    }

    private void setOwner(@NotNull final JPAPerson owner) {
        this.owner = owner;
    }

    public Set<JPAGroup> getGroups() {
        return groups;
    }


    public Set<JPAProject> getProjects() {
        return projects;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ",
                JPAAccount.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("owner='" + owner + "'")
                .add("projectsOwned=" + projects.size())
                .toString();
    }
}
