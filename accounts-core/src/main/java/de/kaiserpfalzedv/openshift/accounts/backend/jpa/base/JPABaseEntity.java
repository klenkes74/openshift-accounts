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
