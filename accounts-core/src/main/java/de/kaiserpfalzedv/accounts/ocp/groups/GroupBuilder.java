/*
 * Copyright (C) 2018 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.kaiserpfalzedv.accounts.ocp.groups;

import de.kaiserpfalzedv.accounts.BuilderValidationException;
import de.kaiserpfalzedv.accounts.users.User;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-09-10
 */
public class GroupBuilder implements Builder<Group> {
    private String ldapServer;
    
    private String dn;

    private String ocpName;

    private OffsetDateTime syncDate;

    private String uuid;

    private String resourceVersion;

    private final HashSet<User> users = new HashSet<>();

    private final HashSet<Group> groups = new HashSet<>();

    
    @Override
    public Group build() {
        validate();

        Group result = new Group(ldapServer, dn, ocpName, syncDate);

        result.addUsers(users);
        result.addGroups(groups);

        if (uuid != null) {
            result.setUuid(uuid);
        }

        if (resourceVersion != null) {
            result.setResourceVersion(resourceVersion);
        }

        return result;
    }

    private void validate() throws BuilderValidationException {
        HashSet<String> failures = new HashSet<>(3);

        if (syncDate == null) {
            syncDate = OffsetDateTime.now(ZoneId.of("UTC"));
        }

        if (isBlank(ldapServer)) {
            failures.add("No ldap server in entry!");
        }

        if (isBlank(dn)) {
            failures.add("No DN in entry!");
        }

        if (isBlank(ocpName)) {
            failures.add("No OCP group name given!");
        }

        if (! failures.isEmpty()) {
            throw new BuilderValidationException(User.class, failures);
        }
    }


    public GroupBuilder withLdapServer(final String ldapServer) {
        this.ldapServer = ldapServer;
        return this;
    }

    public GroupBuilder withSyncDate(final OffsetDateTime syncDate) {
        this.syncDate = syncDate;
        return this;
    }

    public GroupBuilder withDn(final String dn) {
        this.dn = dn;
        return this;
    }

    public GroupBuilder withOcpName(final String ocpName) {
        this.ocpName = ocpName;
        return this;
    }

    public GroupBuilder withResourceVersion(@NotNull  final String resourceVersion) {
        this.resourceVersion = resourceVersion;
        return this;
    }

    public GroupBuilder withUuid(@NotNull final String uuid) {
        this.uuid = uuid;
        return this;
    }


    public GroupBuilder addUser(final User user) {
        this.users.add(user);
        return this;
    }
    
    public GroupBuilder removeUser(final User user) {
        this.users.remove(user);
        return this;
    }

    public GroupBuilder addUsers(final Collection<User> users) {
        this.users.addAll(users);
        return this;
    }
    
    public GroupBuilder removeUsers(final Collection<User> users) {
        this.users.removeAll(users);
        return this;
    }


    public GroupBuilder addGroup(final Group Group) {
        this.groups.add(Group);
        return this;
    }

    public GroupBuilder removeGroup(final Group Group) {
        this.groups.remove(Group);
        return this;
    }

    public GroupBuilder addGroups(final Collection<Group> Groups) {
        this.groups.addAll(Groups);
        return this;
    }

    public GroupBuilder removeGroups(final Collection<Group> Groups) {
        this.groups.removeAll(Groups);
        return this;
    }
}
