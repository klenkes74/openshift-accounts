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

package de.kaiserpfalzedv.openshift.accounts.backend.model;

import de.kaiserpfalzedv.openshift.accounts.backend.dto.PersonImpl;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.BaseEntityBuilder;
import javafx.util.Builder;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class GroupBuilder<T extends Group> extends BaseEntityBuilder<T> implements Builder<T> {
    protected String name;
    protected Account owner;
    protected final Set<Account> accounts = new HashSet<>();


    public GroupBuilder<T> setGroup(@NotNull final Group group) {
        setIdentity(group);
        setVersionable(group);
        setChangeable(group);

        setName(group.getName());
        setOwner(group.getOwner());
        setAccounts(group.getAccounts());

        return this;
    }

    public GroupBuilder<T> setName(@NotNull final String name) {
        this.name = name;
        return this;
    }

    public GroupBuilder<T> setOwner(@NotNull final Account owner) {
        this.owner = owner;
        return this;
    }

    public GroupBuilder<T> setAccounts(@NotNull final Collection<? extends Account> accounts) {
        this.accounts.clear();
        this.accounts.addAll(accounts);
        return this;
    }
}
