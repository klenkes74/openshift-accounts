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

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Identifiable;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Versionable;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.converters.UuidAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-21
 */
@MappedSuperclass
public abstract class JPAIdentifiable implements Identifiable, Versionable {
    @Id
    @Column(name = "ID_", length = 40, nullable = false, unique = true, updatable = false)
    private String storedId;

    @Transient
    private UUID id;


    @Column(name = "TENANT_", length = 40, nullable = false)
    @Convert(converter = UuidAttributeConverter.class)
    private UUID tenant = DEFAULT_TENANT;


    @Version
    private Long version;


    @SuppressWarnings({"unused", "WeakerAccess"})
    public JPAIdentifiable() {
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public JPAIdentifiable(@NotNull final UUID id, final Long version) {
        this.id = id;
        this.version = version;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public JPAIdentifiable(@NotNull final UUID tenant, @NotNull final UUID id, final Long version) {
        this.id = id;
        this.tenant = tenant;
        this.version = version;
    }


    @Override
    public UUID getTenant() {
        return tenant;
    }

    @Override
    public void setTenant(final UUID tenant) {
        this.tenant = tenant;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Long getVersion() {
        return version;
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
        if (!(o instanceof JPAIdentifiable)) return false;
        JPAIdentifiable that = (JPAIdentifiable) o;
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
                JPAIdentifiable.class.getSimpleName() + "@" + System.identityHashCode(this)  + "[",
                "]")
                .add("id=" + id)
                .add("tenant=" + tenant)
                .toString();
    }
}
