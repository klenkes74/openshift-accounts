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

package de.kaiserpfalzedv.openshift.accounts.backend.dto.ocp;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.dto.base.BaseEntityImpl;
import de.kaiserpfalzedv.openshift.accounts.backend.dto.iam.AccountImpl;
import de.kaiserpfalzedv.openshift.accounts.backend.dto.iam.GroupImpl;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Account;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Cluster;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Project;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class ProjectImpl extends BaseEntityImpl implements Project {
    private Cluster cluster;
    private String name;

    private AccountImpl owner;

    private final Set<GroupImpl> viewers = new HashSet<>();
    private final Set<GroupImpl> editors = new HashSet<>();
    private final Set<GroupImpl> admins = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    protected ProjectImpl() {}

    /**
     * Created the new account.
     * @param name The name of the project.
     * @param owner The owner of the project.
     */
    @SuppressWarnings("WeakerAccess")
    public ProjectImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,

            @NotNull final Cluster cluster,
            @NotNull final String name,
            @NotNull final Account owner,

            @NotNull final Collection<? extends Group> viewers,
            @NotNull final Collection<? extends Group> editors,
            @NotNull final Collection<? extends Group> admins
    ) {
        super(id, version, created, modified);

        setCluster(cluster);
        setName(name);
        setOwner(owner);

        setViewers(viewers);
        setEditors(editors);
        setAdmins(admins);
    }

    @SuppressWarnings("WeakerAccess")
    public ProjectImpl(@NotNull final Project orig) {
        this(orig.getId(), orig.getVersion(), orig.getCreated(), orig.getModified(),
             orig.getCluster(), orig.getName(), orig.getOwner(),
             orig.getViewers(), orig.getEditors(), orig.getAdmins());
    }


    @Override
    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(@NotNull final Cluster cluster) {
        this.cluster = (cluster instanceof ClusterImpl ? (ClusterImpl) cluster : new ClusterImpl(cluster));
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

    public void setOwner(@NotNull final Account owner) {
        this.owner = (owner instanceof AccountImpl ? (AccountImpl) owner : new AccountImpl(owner));
    }


    public Set<? extends Group> getViewers() {
        return viewers;
    }

    public void clearViewers() {
        viewers.clear();
    }

    public void setViewers(@NotNull final Collection<? extends Group> groups) {
        clearViewers();
        addViewers(groups);
    }

    public void addViewers(@NotNull final Collection<? extends Group> groups) {
        viewers.addAll(convertGroups(groups));
    }

    private Set<GroupImpl> convertGroups(@NotNull final Collection<? extends Group> origs) {
        HashSet<GroupImpl> result = new HashSet<>(origs.size());

        origs.forEach(g -> result.add(convertGroup(g)));

        return result;
    }

    private static GroupImpl convertGroup(@NotNull final Group orig) {
        return (orig instanceof GroupImpl ? (GroupImpl) orig : new GroupImpl(orig));
    }

    public void addViewer(@NotNull final Group group) {
        viewers.add(convertGroup(group));
    }

    public void removeViewers(@NotNull final Collection<? extends Group> groups) {
        viewers.removeAll(convertGroups(groups));
    }

    public void removeViewer(@NotNull final Group group) {
        viewers.remove(convertGroup(group));
    }


    public Set<? extends Group> getEditors() {
        return editors;
    }

    public void clearEditors() {
        editors.clear();
    }

    public void setEditors(@NotNull final Collection<? extends Group> groups) {
        clearEditors();
        addEditors(groups);
    }

    public void addEditors(@NotNull final Collection<? extends Group> groups) {
        editors.addAll(convertGroups(groups));
    }

    public void addEditor(@NotNull final Group group) {
        editors.add(convertGroup(group));
    }

    public void removeEditors(@NotNull final Collection<? extends Group> groups) {
        editors.removeAll(convertGroups(groups));
    }

    public void removeEditor(@NotNull final Group group) {
        editors.remove(convertGroup(group));
    }


    public Set<? extends Group> getAdmins() {
        return admins;
    }

    public void clearAdmins() {
        admins.clear();
    }

    public void setAdmins(@NotNull final Collection<? extends Group> groups) {
        clearAdmins();
        addAdmins(groups);
    }

    public void addAdmins(@NotNull final Collection<? extends Group> groups) {
        admins.addAll(convertGroups(groups));
    }

    public void addAdmin(@NotNull final Group group) {
        admins.add(convertGroup(group));
    }

    public void removeAdmins(@NotNull final Collection<? extends Group> groups) {
        admins.removeAll(convertGroups(groups));
    }

    public void removeAdmin(@NotNull final Group group) {
        admins.remove(convertGroup(group));
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                ProjectImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("owner='" + owner + "'")
                .add("name='" + name + "'")
                .toString();
    }
}
