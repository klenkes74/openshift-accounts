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
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.BaseEntity;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class BaseEntityImpl implements BaseEntity {
    private UUID id = UUID.randomUUID();
    private Long version;

    private OffsetDateTime created;
    private OffsetDateTime modified;


    @SuppressWarnings({"deprecation", "DeprecatedIsStillUsed"})
    @Deprecated
    protected BaseEntityImpl() {}

    @SuppressWarnings("unused")
    public BaseEntityImpl(@NotNull final UUID id) {
        setId(id);
    }

    @SuppressWarnings({"deprecation", "WeakerAccess"})
    public BaseEntityImpl(
            @NotNull final UUID id,
            final Long version
    ) {
        setId(id);
        setVersion(version);
    }

    @SuppressWarnings("unused")
    public BaseEntityImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified
    ) {
        setId(id);
        setVersion(version);

        setCreated(created);
        setModified(modified);
    }


    @Override
    public UUID getId() {
        return id;
    }

    public void setId(@NotNull final UUID id) {
        this.id = id;
    }


    @Override
    public Long getVersion() {
        return version;
    }

    private void setVersion(final Long version) {
        this.version = version;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntityImpl)) return false;
        BaseEntityImpl that = (BaseEntityImpl) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                BaseEntityImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id=" + id)
                .add("version=" + version)
                .toString();
    }
}
