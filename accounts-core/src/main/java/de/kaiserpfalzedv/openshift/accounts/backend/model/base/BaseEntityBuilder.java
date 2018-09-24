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

package de.kaiserpfalzedv.openshift.accounts.backend.model.base;

import de.kaiserpfalzedv.openshift.accounts.backend.dto.base.BaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;
import javafx.scene.control.TextFormatter;
import javafx.util.Builder;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class BaseEntityBuilder<T extends Identifiable> implements Builder<T> {
    protected UUID id = UUID.randomUUID();
    protected Long version;
    protected UUID tenant = Project.DEFAULT_TENANT;

    protected OffsetDateTime created;
    protected OffsetDateTime modified;


    public BaseEntityBuilder<T> setIdentity(@NotNull final Identifiable orig) {
        setId(orig.getId());
        setTenant(orig.getTenant());
        return this;
    }


    public BaseEntityBuilder<T> setVersionable(@NotNull final Versionable orig) {
        setVersion(orig.getVersion());
        return this;
    }


    public BaseEntityBuilder<T> setChangeable(@NotNull final Changeable orig) {
        setCreated(orig.getCreated());
        setModified(orig.getModified());
        return this;
    }


    public BaseEntityBuilder<T> setId(UUID id) {
        this.id = id;
        return this;
    }

    public BaseEntityBuilder<T> setVersion(Long version) {
        this.version = version;
        return this;
    }

    public BaseEntityBuilder<T> setTenant(UUID tenant) {
        this.tenant = tenant;
        return this;
    }

    public BaseEntityBuilder<T> setCreated(OffsetDateTime created) {
        this.created = created;
        return this;
    }

    public BaseEntityBuilder<T> setModified(OffsetDateTime modified) {
        this.modified = modified;
        return this;
    }
}
