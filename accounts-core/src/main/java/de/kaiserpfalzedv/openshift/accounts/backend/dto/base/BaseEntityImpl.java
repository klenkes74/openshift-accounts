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

package de.kaiserpfalzedv.openshift.accounts.backend.dto.base;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.BaseEntity;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class BaseEntityImpl extends IdentifiableImpl implements BaseEntity {

    private OffsetDateTime created;
    private OffsetDateTime modified;


    @Deprecated
    public BaseEntityImpl() {}

    public BaseEntityImpl(@NotNull final UUID id) {
        super(id);
    }

    public BaseEntityImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final UUID tenant
    ) {
        super(id, version, tenant);
    }

    public BaseEntityImpl(
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
