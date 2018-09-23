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

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class GroupImpl extends BaseEntity implements Group {
    private String name;

    private AccountImpl owner;

    private Set<AccountImpl> accounts = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public GroupImpl() {}

    /**
     * Created the new account.
     * @param name The name of the project.
     * @param owner The owner of the project.
     */
    @SuppressWarnings("unused")
    public GroupImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final UUID tenant,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            final String name,
            final AccountImpl owner) {
        super(id, version, tenant, created, modified);

        setName(name);
        setOwner(owner);
    }


    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }


    public AccountImpl getOwner() {
        return owner;
    }

    public void setOwner(@NotNull final AccountImpl account) {
        this.owner = account;
    }


    public Set<? extends Account> getAccounts() {
        return accounts;
    }

    public void clearAccounts() {
        accounts.clear();
    }

    public void setAccounts(@NotNull final Collection<? extends AccountImpl> accounts) {
        clearAccounts();

        this.accounts.addAll(accounts);
    }

    public void addAccounts(@NotNull final Collection<? extends AccountImpl> accounts) {
        this.accounts.addAll(accounts);
    }

    public void addAccount(@NotNull final AccountImpl account) {
        this.accounts.add(account);
    }

    public void removeAccounts(@NotNull final Collection<? extends Account> accounts) {
        this.accounts.removeAll(accounts);
    }

    public void removeAccount(@NotNull final Account account) {
        this.accounts.remove(account);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                GroupImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("owner='" + owner + "'")
                .add("name='" + name + "'")
                .add("accountCount=" + accounts.size())
                .toString();
    }
}
