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

package de.kaiserpfalzedv.openshift.accounts.backend.jpa.iam;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Account;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Group;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Entity(name = "Groups")
@Table(
        name = "GROUPS"
)
public class JPAGroup extends JPABaseEntity implements Group {
    @Column(name = "NAME_", length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER_", nullable = false)
    private JPAAccount owner;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "GROUPS_ACCOUNTS",
            joinColumns = { @JoinColumn(name = "GROUP_", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "ACCOUNT_", nullable = false) }
    )
    private Set<JPAAccount> accounts = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public JPAGroup() {super();}

    /**
     * Created the new account.
     * @param name The name of the project.
     * @param owner The owner of the project.
     */
    @SuppressWarnings("unused")
    public JPAGroup(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final Account owner,
            @NotNull final Collection<? extends Account> accounts
    ) {
        super(id, version, created, modified);

        setName(name);
        setOwner(owner);
        setAccounts(accounts);
    }

    public JPAGroup(@NotNull final Group group) {
        this(group.getId(), group.getVersion(),
                group.getCreated(), group.getModified(),
                group.getName(), group.getOwner(), group.getAccounts());
    }


    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }


    public JPAAccount getOwner() {
        return owner;
    }

    public void setOwner(@NotNull final Account account) {
        this.owner = convertAccount(account);
    }

    private JPAAccount convertAccount(@NotNull final Account account) {
        return (account instanceof JPAAccount ? (JPAAccount) account : new JPAAccount(account));
    }


    public Set<? extends Account> getAccounts() {
        return accounts;
    }

    public void clearAccounts() {
        accounts.clear();
    }

    public void setAccounts(@NotNull final Collection<? extends Account> accounts) {
        clearAccounts();
        addAccounts(accounts);
    }

    public void addAccounts(@NotNull final Collection<? extends Account> accounts) {
        this.accounts.addAll(convertAccounts(accounts));
    }

    private Collection<? extends JPAAccount> convertAccounts(@NotNull final Collection<? extends Account> accounts) {
        HashSet<JPAAccount> result = new HashSet<>(accounts.size());
        accounts.forEach(a -> result.add(convertAccount(a)));
        return result;
    }

    public void addAccount(@NotNull final Account account) {
        this.accounts.add(convertAccount(account));
    }

    public void removeAccounts(@NotNull final Collection<? extends Account> accounts) {
        this.accounts.removeAll(convertAccounts(accounts));
    }

    public void removeAccount(@NotNull final Account account) {
        this.accounts.remove(convertAccount(account));
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                JPAGroup.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("version=" + getVersion())
                .add("created=" + getCreated())
                .add("modified=" + getModified())
                .add("owner='" + owner + "'")
                .add("name='" + name + "'")
                .add("accountCount=" + accounts.size())
                .toString();
    }
}
