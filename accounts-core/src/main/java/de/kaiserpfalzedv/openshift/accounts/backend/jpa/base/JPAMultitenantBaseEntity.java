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
import java.util.StringJoiner;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.converters.UuidAttributeConverter;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Tenantable;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-21
 */
@MappedSuperclass
public abstract class JPAMultitenantBaseEntity extends JPABaseEntity implements Tenantable {
    @Column(name = "TENANT_", length = 40, nullable = false)
    @Convert(converter = UuidAttributeConverter.class)
    private UUID tenant = DEFAULT_TENANT;

    @SuppressWarnings({"unused", "deprecation"})
    protected JPAMultitenantBaseEntity() {
        super();
    }

    @SuppressWarnings("unused")
    public JPAMultitenantBaseEntity(@NotNull final UUID id, final Long version, @NotNull final UUID tenant) {
        super(id, version);

        setTenant(tenant);
    }

    @SuppressWarnings("unused")
    public JPAMultitenantBaseEntity(@NotNull final UUID id, final Long version, @NotNull final UUID tenant,
                                    @NotNull final OffsetDateTime created, @NotNull final OffsetDateTime modified) {
        super(id, version, created, modified);

        setTenant(tenant);
    }

    @Override
    public UUID getTenant() {
        return tenant;
    }

    private void setTenant(@NotNull final UUID tenant) {
        this.tenant = tenant;
    }


    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                JPAMultitenantBaseEntity.class.getSimpleName() + "@" + System.identityHashCode(this)  + "[",
                "]")
                .add("id=" + getId())
                .add("version=" + getVersion())
                .add("tenant=" + tenant)
                .add("created=" + getCreated())
                .add("modified=" + getModified())
                .toString();
    }
}
