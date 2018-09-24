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

package de.kaiserpfalzedv.openshift.accounts.backend.dto.iam;

import java.time.OffsetDateTime;
import java.util.StringJoiner;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.dto.base.BaseEntityImpl;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Person;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class PersonImpl extends BaseEntityImpl implements Person {

    private String name;
    private String emailAddress;


    @SuppressWarnings("deprecation")
    @Deprecated
    protected PersonImpl() {}

    @SuppressWarnings("WeakerAccess")
    public PersonImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final String emailAddress
    ) {
        super(id, version, created, modified);

        setName(name);
        setEmailAddress(emailAddress);
    }

    @SuppressWarnings("WeakerAccess")
    public PersonImpl(@NotNull final Person orig) {
        this(orig.getId(), orig.getVersion(),
             orig.getCreated(), orig.getModified(),
             orig.getName(), orig.getEmailAddress());
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NotNull final String name) {
        this.name = name;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(@NotNull final String emailAddress) {
        this.emailAddress = emailAddress;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                GroupImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("version=" + getVersion())
                .add("created=" + getCreated())
                .add("modified=" + getModified())
                .add("name='" + name + "'")
                .add("emailAddress='" + emailAddress + "'")
                .toString();
    }
}
