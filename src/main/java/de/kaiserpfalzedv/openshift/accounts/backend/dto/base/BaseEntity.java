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

package de.kaiserpfalzedv.openshift.accounts.backend.dto.base;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Changeable;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Versionable;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class BaseEntity extends Identifiable implements de.kaiserpfalzedv.openshift.accounts.backend.model.base.Identifiable, Versionable, Changeable {

    private OffsetDateTime created;
    private OffsetDateTime modified;


    @Deprecated
    public BaseEntity() {}

    public BaseEntity(@NotNull final UUID id) {
        super(id);
    }

    public BaseEntity(
            @NotNull final UUID id,
            final Long version,
            @NotNull final UUID tenant
    ) {
        super(id, version, tenant);
    }

    public BaseEntity(
            @NotNull final UUID id,
            final Long version,
            @NotNull final UUID tenant,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified
    ) {
        this(id, version, tenant);

        setCreated(created);
        setModified(modified);
    }


    @Override
    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(@NotNull final OffsetDateTime created) {
        this.created = created;
    }

    @Override
    public OffsetDateTime getModified() {
        return modified;
    }

    public void setModified(@NotNull final OffsetDateTime modified) {
        this.modified = modified;
    }
}
