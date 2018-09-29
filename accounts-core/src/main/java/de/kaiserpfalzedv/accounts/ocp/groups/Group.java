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

import de.kaiserpfalzedv.accounts.ocp.OcpNameHolder;
import de.kaiserpfalzedv.accounts.users.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-09-10
 */
public class Group implements OcpNameHolder, Serializable {
    public static final String LOCAL_PART = "/oapi/v1/groups/";

    private String ldapServer;
    private String dn;
    private String ocpName;

    private OffsetDateTime syncDate;
    private String uuid;
    private String resourceVersion;

    private final HashSet<User> users = new HashSet<>();

    private final HashSet<Group> groups = new HashSet<>();


    public Group(final String ldapServer, final String dn, final String ocpName, final OffsetDateTime syncDate) {
        this.ldapServer = ldapServer;
        this.dn = dn;
        this.ocpName = ocpName;
        this.syncDate = syncDate;
    }

    void setUuid(@NotNull final String uuid) {
        this.uuid = uuid;
    }

    void setResourceVersion(@NotNull final String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    boolean addUser(final User user) {
        return users.add(user);
    }

    boolean removeUser(final User user) {
        return users.remove(user);
    }

    void clearUsers() {
        users.clear();
    }

    boolean addUsers(final Collection<User> users) {
        return this.users.addAll(users);
    }


    boolean addGroup(final Group group) {
        return groups.add(group);
    }

    boolean removeGroup(final Group group) {
        return groups.remove(group);
    }

    void clearGroups() {
        groups.clear();
    }

    boolean addGroups(final Collection<Group> groups) {
        return this.groups.addAll(groups);
    }


    public String getLdapServer() {
        return ldapServer;
    }

    public String getDn() {
        return dn;
    }

    public String getOcpName() {
        return ocpName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }


    public Set<User> getUsers() {
        return users;
    }

    public Set<User> getEffectiveUsers() {
        HashSet<User> result = new HashSet<>();
        result.addAll(users);

        return addEffectiveUser(result, groups);
    }

    private Set<User> addEffectiveUser(Set<User> effectiveUsers, Set<Group> groups) {
        for (Group group : groups) {
            effectiveUsers.addAll(group.getEffectiveUsers());
        }

        return effectiveUsers;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public OffsetDateTime getSyncDate() {
        return syncDate;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Group rhs = (Group) obj;
        return new EqualsBuilder()
                .append(this.ldapServer, rhs.ldapServer)
                .append(this.dn, rhs.dn)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(ldapServer)
                .append(dn)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ldapServer", ldapServer)
                .append("dn", dn)
                .append("ocpName", ocpName)
                .append("users", users)
                .append("groups", groups)
                .toString();
    }
}
