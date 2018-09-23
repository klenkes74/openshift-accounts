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
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class PersonImpl extends BaseEntity implements de.kaiserpfalzedv.openshift.accounts.backend.model.Person {

    private String name;
    private String emailAddress;


    @Deprecated
    public PersonImpl() {}

    public PersonImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final String emailAddress
    ) {
        super(id, version, Project.DEFAULT_TENANT, created, modified);

        setName(name);
        setEmailAddress(emailAddress);
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
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(@NotNull final String emailAddress) {
        this.emailAddress = emailAddress;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                GroupImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("name='" + name + "'")
                .add("emailAddress='" + emailAddress + "'")
                .toString();
    }
}
