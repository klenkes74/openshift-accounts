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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.dto.base.BaseEntityImpl;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Account;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Group;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class GroupImpl extends BaseEntityImpl implements Group {
    private String name;

    private AccountImpl owner;

    private Set<AccountImpl> accounts = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    protected GroupImpl() {}

    /**
     * Created the new account.
     * @param name The name of the project.
     * @param owner The owner of the project.
     */
    @SuppressWarnings({"unused", "WeakerAccess"})
    public GroupImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final UUID tenant,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            final String name,
            final AccountImpl owner) {
        super(id, version, tenant, created, modified);

        setName(name);
        setOwner(owner);
    }

    @SuppressWarnings("WeakerAccess")
    public GroupImpl(@NotNull final Group orig) {
        this(orig.getId(), orig.getVersion(), orig.getTenant(),
             orig.getCreated(), orig.getModified(),
             orig.getName(), convertAccount(orig.getOwner()));
    }

    private static AccountImpl convertAccount(@NotNull final Account orig) {
        return (orig instanceof AccountImpl ? (AccountImpl) orig : new AccountImpl(orig));
    }


    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }


    public AccountImpl getOwner() {
        return owner;
    }

    public void setOwner(@NotNull final AccountImpl account) {
        this.owner = account;
    }


    public Set<? extends Account> getAccounts() {
        return accounts;
    }

    public void clearAccounts() {
        accounts.clear();
    }

    public void setAccounts(@NotNull final Collection<? extends AccountImpl> accounts) {
        clearAccounts();

        this.accounts.addAll(accounts);
    }

    public void addAccounts(@NotNull final Collection<? extends AccountImpl> accounts) {
        this.accounts.addAll(accounts);
    }

    public void addAccount(@NotNull final AccountImpl account) {
        this.accounts.add(account);
    }

    public void removeAccounts(@NotNull final Collection<? extends Account> accounts) {
        this.accounts.removeAll(accounts);
    }

    public void removeAccount(@NotNull final Account account) {
        this.accounts.remove(account);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                GroupImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("owner='" + owner + "'")
                .add("name='" + name + "'")
                .add("accountCount=" + accounts.size())
                .toString();
    }
}
