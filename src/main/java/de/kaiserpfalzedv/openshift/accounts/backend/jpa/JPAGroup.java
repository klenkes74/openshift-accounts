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
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Entity(name = "Groups")
@Table(
        name = "GROUPS"
)
public class JPAGroup extends JPABaseEntity {
    @Column(name = "NAME_", length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER_", nullable = false)
    private JPAAccount owner;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "GROUPS_ACCOUNTS",
            joinColumns = { @JoinColumn(name = "GROUP_", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "ACCOUNT_", nullable = false) }
    )
    private Set<JPAAccount> accounts = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public JPAGroup() {}

    /**
     * Created the new account.
     * @param name The name of the project.
     * @param owner The owner of the project.
     */
    @SuppressWarnings("unused")
    public JPAGroup(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final JPAAccount owner,
            @NotNull final Collection<? extends JPAAccount> accounts
    ) {
        super(id, version, created, modified);

        setName(name);
        setOwner(owner);
        setAccounts(accounts);
    }

    public JPAGroup(@NotNull final Group group) {
        this(group.getId(), group.getVersion(),
                group.getCreated(), group.getModified(),
                group.getName(), convertAccount(group.getOwner()), convertAccounts(group.getAccounts()));
    }

    private static Collection<? extends JPAAccount> convertAccounts(@NotNull final Collection<? extends Account> accounts) {
        HashSet<JPAAccount> result = new HashSet<>(accounts.size());

        accounts.forEach(a -> result.add(convertAccount(a)));

        return result;
    }

    private static JPAAccount convertAccount(@NotNull final Account account) {
        return (account instanceof JPAAccount ? (JPAAccount) account : new JPAAccount(account));
    }


    public String getName() {
        return name;
    }

    private void setName(@NotNull final String name) {
        this.name = name;
    }


    public JPAAccount getOwner() {
        return owner;
    }

    public void setOwner(@NotNull final JPAAccount account) {
        this.owner = account;
    }


    public Set<JPAAccount> getAccounts() {
        return accounts;
    }

    public void clearAccounts() {
        accounts.clear();
    }

    public void setAccounts(@NotNull final Collection<? extends JPAAccount> accounts) {
        clearAccounts();

        this.accounts.addAll(accounts);
    }

    public void addAccounts(@NotNull final Collection<? extends JPAAccount> accounts) {
        this.accounts.addAll(accounts);
    }

    public void addAccount(@NotNull final JPAAccount account) {
        this.accounts.add(account);
    }

    public void removeAccounts(@NotNull final Collection<? extends JPAAccount> accounts) {
        this.accounts.removeAll(accounts);
    }

    public void removeAccount(@NotNull final JPAAccount account) {
        this.accounts.remove(account);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                JPAGroup.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("owner='" + owner + "'")
                .add("name='" + name + "'")
                .add("accountCount=" + accounts.size())
                .toString();
    }
}
