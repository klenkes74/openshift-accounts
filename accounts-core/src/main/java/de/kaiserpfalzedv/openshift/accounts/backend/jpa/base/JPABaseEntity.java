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

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.converters.OffsetDateTimeAttributeConverter;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.converters.UuidAttributeConverter;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.BaseEntity;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-21
 */
@MappedSuperclass
public abstract class JPABaseEntity implements BaseEntity {
    @Id
    @Column(name = "ID_", length = 40, nullable = false, unique = true, updatable = false)
    private String storedId;

    @Transient
    private UUID id;

    @Version
    private Long version;


    @Column(name = "_CREATED_", nullable = false, updatable = false)
    @Convert(converter = OffsetDateTimeAttributeConverter.class)
    private OffsetDateTime created = OffsetDateTime.now(UTC);

    @Column(name = "_MODIFIED_", nullable = false)
    @Convert(converter = OffsetDateTimeAttributeConverter.class)
    private OffsetDateTime modified = OffsetDateTime.now(UTC);


    @SuppressWarnings({"unused", "DeprecatedIsStillUsed"})
    @Deprecated
    protected JPABaseEntity() {}

    @SuppressWarnings({"unused", "WeakerAccess"})
    public JPABaseEntity(@NotNull final UUID id, final Long version) {
        setId(id);
        setVersion(version);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public JPABaseEntity(@NotNull final UUID id, final Long version,
                         @NotNull final OffsetDateTime created, @NotNull final OffsetDateTime modified) {
        setId(id);
        setVersion(version);

        setCreated(created);
        setModified(modified);
    }


    @Override
    public UUID getId() {
        return id;
    }

    private void setId(@NotNull final UUID id) {
        this.id = id;
    }

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


    @PrePersist
    private void convertIdToString() {
        storedId = new UuidAttributeConverter().convertToDatabaseColumn(id);
    }

    @PostLoad
    private void convertStringToId() {
        id = new UuidAttributeConverter().convertToEntityAttribute(storedId);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JPABaseEntity)) return false;
        JPABaseEntity that = (JPABaseEntity) o;
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
                JPABaseEntity.class.getSimpleName() + "@" + System.identityHashCode(this)  + "[",
                "]")
                .add("id=" + id)
                .add("version=" + version)
                .add("created=" + created)
                .add("modified=" + modified)
                .toString();
    }
}