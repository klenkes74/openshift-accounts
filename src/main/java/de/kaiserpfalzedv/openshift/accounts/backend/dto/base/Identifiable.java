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

import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Versionable;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class Identifiable implements de.kaiserpfalzedv.openshift.accounts.backend.model.base.Identifiable, Versionable {
    private UUID id = UUID.randomUUID();
    private Long version;
    private UUID tenant = Project.DEFAULT_TENANT;

    /**
     * @deprecated Only for JAX-RS, JAX-B, JPA, ...
     */
    @Deprecated
    public Identifiable() {}

    public Identifiable(@NotNull final UUID id) {
        this.id = id;
    }

    public Identifiable(
            @NotNull final UUID id,
            final Long version,
            @NotNull final UUID tenant
    ) {
        this.id = id;
        this.version = version;
        this.tenant = tenant;
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


    @Override
    public UUID getTenant() {
        return tenant;
    }

    @Override
    public void setTenant(@NotNull final UUID tenant) {
        this.tenant = tenant;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identifiable)) return false;
        Identifiable that = (Identifiable) o;
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
                Identifiable.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id=" + id)
                .add("version=" + version)
                .add("tenant=" + tenant)
                .toString();
    }
}
