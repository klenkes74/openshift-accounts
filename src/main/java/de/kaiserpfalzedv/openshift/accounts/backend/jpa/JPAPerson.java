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

import de.kaiserpfalzedv.openshift.accounts.backend.model.Person;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Entity(name = "Persons")
@Table(
        name = "PERSONS",
        indexes = {
                @Index(name = "PERSON_NAME_IDX", columnList = "NAME_")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "PERSON_NAME_UK", columnNames = {"NAME_"})
        }
)
public class JPAPerson extends JPABaseEntity {
    @Column(name = "NAME_", length = 100, nullable = false)
    private String name;

    @Column(name = "EMAIL_", length = 100, nullable = false)
    private String email;


    public JPAPerson() {}

    public JPAPerson(@NotNull final String name) {
        this.name = name;
    }

    public JPAPerson(@NotNull final String name, @NotNull final String email) {
        this.name = name;
        this.email = email;
    }

    public JPAPerson(@NotNull final UUID id, @NotNull final Long version,
                     @NotNull final OffsetDateTime created, @NotNull final OffsetDateTime modified,
                     @NotNull final String name, @NotNull final String email) {
        super(id, version, created, modified);

        setName(name);
        setEmailAddress(email);
    }

    public JPAPerson(@NotNull final Person orig) {
        this(orig.getId(), orig.getVersion(),
                orig.getCreated(), orig.getModified(),
                orig.getName(), orig.getEmailAddress());
    }


    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }


    public String getEmailAddress() {
        return email;
    }

    public void setEmailAddress(@NotNull final String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                JPAPerson.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("name='" + name + "'")
                .add("email='" + email + "'")
                .toString();
    }
}
