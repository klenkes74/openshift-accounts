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

import de.kaiserpfalzedv.openshift.accounts.backend.dto.PersonImpl;
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
public abstract class PersonBuilder<T extends Person> extends BaseEntityBuilder<T> implements Builder<T> {
    protected String name;
    protected String emailAddress;

    public PersonBuilder<T> setPerson(@NotNull Person person) {
        setId(person.getId());
        setVersion(person.getVersion());
        setTenant(person.getTenant());

        setCreated(person.getCreated());
        setModified(person.getModified());

        setName(person.getName());
        setEmailAddress(person.getEmailAddress());

        return this;
    }

    public PersonBuilder<T> setName(@NotNull final String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder<T> setEmailAddress(@NotNull final String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }
}
