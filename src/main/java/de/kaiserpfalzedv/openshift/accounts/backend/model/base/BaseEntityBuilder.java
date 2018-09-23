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

package de.kaiserpfalzedv.openshift.accounts.backend.model.base;

import de.kaiserpfalzedv.openshift.accounts.backend.dto.base.BaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;
import javafx.scene.control.TextFormatter;
import javafx.util.Builder;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class BaseEntityBuilder<T extends Identifiable> implements Builder<T> {
    protected UUID id = UUID.randomUUID();
    protected Long version;
    protected UUID tenant = Project.DEFAULT_TENANT;

    protected OffsetDateTime created;
    protected OffsetDateTime modified;


    public BaseEntityBuilder<T> setIdentity(@NotNull final Identifiable orig) {
        setId(orig.getId());
        setTenant(orig.getTenant());
        return this;
    }


    public BaseEntityBuilder<T> setVersionable(@NotNull final Versionable orig) {
        setVersion(orig.getVersion());
        return this;
    }


    public BaseEntityBuilder<T> setChangeable(@NotNull final Changeable orig) {
        setCreated(orig.getCreated());
        setModified(orig.getModified());
        return this;
    }


    public BaseEntityBuilder<T> setId(UUID id) {
        this.id = id;
        return this;
    }

    public BaseEntityBuilder<T> setVersion(Long version) {
        this.version = version;
        return this;
    }

    public BaseEntityBuilder<T> setTenant(UUID tenant) {
        this.tenant = tenant;
        return this;
    }

    public BaseEntityBuilder<T> setCreated(OffsetDateTime created) {
        this.created = created;
        return this;
    }

    public BaseEntityBuilder<T> setModified(OffsetDateTime modified) {
        this.modified = modified;
        return this;
    }
}
