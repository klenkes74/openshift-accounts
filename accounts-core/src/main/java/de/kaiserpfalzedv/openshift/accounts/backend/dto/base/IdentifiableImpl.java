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

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Project;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Identifiable;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Versionable;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class IdentifiableImpl implements Identifiable, Versionable {
    private UUID id = UUID.randomUUID();
    private Long version;
    private UUID tenant = Project.DEFAULT_TENANT;

    /**
     * @deprecated Only for JAX-RS, JAX-B, JPA, ...
     */
    @Deprecated
    public IdentifiableImpl() {}

    public IdentifiableImpl(@NotNull final UUID id) {
        this.id = id;
    }

    public IdentifiableImpl(
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
        if (!(o instanceof IdentifiableImpl)) return false;
        IdentifiableImpl that = (IdentifiableImpl) o;
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
                IdentifiableImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id=" + id)
                .add("version=" + version)
                .add("tenant=" + tenant)
                .toString();
    }
}
