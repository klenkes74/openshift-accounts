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

package de.kaiserpfalzedv.openshift.accounts.backend.jpa.iam;

import java.time.OffsetDateTime;
import java.util.StringJoiner;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Person;

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
