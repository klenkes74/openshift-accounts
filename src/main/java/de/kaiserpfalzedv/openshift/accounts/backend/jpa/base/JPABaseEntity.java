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

package de.kaiserpfalzedv.openshift.accounts.backend.jpa.base;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Changeable;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.converters.OffsetDateTimeAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-21
 */
@MappedSuperclass
public abstract class JPABaseEntity extends JPAIdentifiable implements Changeable {
    @Column(name = "_CREATED_", nullable = false, updatable = false)
    @Convert(converter = OffsetDateTimeAttributeConverter.class)
    private OffsetDateTime created = OffsetDateTime.now(UTC);

    @Column(name = "_MODIFIED_", nullable = false)
    @Convert(converter = OffsetDateTimeAttributeConverter.class)
    private OffsetDateTime modified = OffsetDateTime.now(UTC);


    @SuppressWarnings("unused")
    public JPABaseEntity() {
    }

    @SuppressWarnings("unused")
    public JPABaseEntity(@NotNull final UUID id, final Long version) {
        super(id, version);
    }

    @SuppressWarnings({"unused"})
    public JPABaseEntity(@NotNull final UUID tenant, @NotNull final UUID id, final Long version) {
        super(tenant, id, version);
    }

    @SuppressWarnings("unused")
    public JPABaseEntity(@NotNull final UUID id, final Long version,
                         @NotNull final OffsetDateTime created, @NotNull final OffsetDateTime modified) {
        super(id, version);

        setCreated(created);
        setModified(modified);
    }

    @SuppressWarnings({"unused"})
    public JPABaseEntity(@NotNull final UUID tenant, @NotNull final UUID id, final Long version,
                         @NotNull final OffsetDateTime created, @NotNull final OffsetDateTime modified) {
        super(tenant, id, version);

        setCreated(created);
        setModified(modified);
    }


    @Override
    public OffsetDateTime getCreated() {
        return created;
    }

    private void setCreated(@NotNull final OffsetDateTime created) {
        this.created = created;
    }

    @Override
    public OffsetDateTime getModified() {
        return modified;
    }

    private void setModified(@NotNull final OffsetDateTime modified) {
        this.modified = modified;
    }


    @PreUpdate
    private void setModified() {
        modified = OffsetDateTime.now(UTC);
    }
}
