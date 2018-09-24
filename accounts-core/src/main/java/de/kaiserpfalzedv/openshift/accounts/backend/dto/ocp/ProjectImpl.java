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

            @NotNull final ClusterImpl cluster,
            @NotNull final String name,
            @NotNull final AccountImpl owner,

            @NotNull final Collection<? extends GroupImpl> viewers,
            @NotNull final Collection<? extends GroupImpl> editors,
            @NotNull final Collection<? extends GroupImpl> admins
    ) {
        super(id, version, Project.DEFAULT_TENANT, created, modified);

        setCluster(cluster);
        setName(name);
        setOwner(owner);
    }

    @SuppressWarnings("WeakerAccess")
    public ProjectImpl(@NotNull final Project orig) {
        this(orig.getId(), orig.getVersion(),
             orig.getCreated(), orig.getModified(),
             convertCluster(orig.getCluster()), orig.getName(), convertAccount(orig.getOwner()),
             convertGroups(orig.getViewers()), convertGroups(orig.getEditors()), convertGroups(orig.getAdmins()));
    }

    private static ClusterImpl convertCluster(@NotNull final Cluster orig) {
        return (orig instanceof ClusterImpl ? (ClusterImpl) orig : new ClusterImpl(orig));
    }

    private static AccountImpl convertAccount(@NotNull final Account orig) {
        return (orig instanceof AccountImpl ? (AccountImpl) orig : new AccountImpl(orig));
    }

    private static Set<GroupImpl> convertGroups(@NotNull final Collection<? extends Group> origs) {
        HashSet<GroupImpl> result = new HashSet<>(origs.size());

        origs.forEach(g -> result.add(convertGroup(g)));

        return result;
    }

    private static GroupImpl convertGroup(@NotNull final Group orig) {
        return (orig instanceof GroupImpl ? (GroupImpl) orig : new GroupImpl(orig));
    }


    @Override
    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(@NotNull final ClusterImpl cluster) {
        this.cluster = cluster;
    }

    public String getName() {
        return name;
    }

    private void setName(@NotNull final String name) {
        this.name = name;
    }


    public AccountImpl getOwner() {
        return owner;
    }

    public void setOwner(@NotNull final AccountImpl owner) {
        this.owner = owner;
    }


    public Set<GroupImpl> getViewers() {
        return viewers;
    }

    public void clearViewers() {
        viewers.clear();
    }

    public void setViewers(Collection<? extends GroupImpl> groups) {
        clearViewers();
        addViewers(groups);
    }

    public void addViewers(@NotNull final Collection<? extends GroupImpl> groups) {
        viewers.addAll(groups);
    }



    public void removeViewers(@NotNull final Collection<? extends GroupImpl> groups) {
        viewers.removeAll(groups);
    }

    public void addViewer(@NotNull final GroupImpl group) {
        viewers.add(group);
    }

    public void removeViewer(@NotNull final GroupImpl group) {
        viewers.remove(group);
    }


    public Set<GroupImpl> getEditors() {
        return editors;
    }

    public void clearEditors() {
        editors.clear();
    }

    public void setEditors(@NotNull final Collection<? extends GroupImpl> groups) {
        clearEditors();
        addEditors(groups);
    }

    public void addEditors(@NotNull final Collection<? extends GroupImpl> groups) {
        editors.addAll(groups);
    }

    public void removeEditors(@NotNull final Collection<? extends GroupImpl> groups) {
        editors.removeAll(groups);
    }

    public void addEditor(@NotNull final GroupImpl group) {
        editors.add(group);
    }

    public void removeEditor(@NotNull final GroupImpl group) {
        editors.remove(group);
    }


    public Set<GroupImpl> getAdmins() {
        return admins;
    }

    public void clearAdmins() {
        admins.clear();
    }

    public void setAdmins(@NotNull final Collection<? extends GroupImpl> groups) {
        clearAdmins();
        addAdmins(groups);
    }

    public void addAdmins(@NotNull final Collection<? extends GroupImpl> groups) {
        admins.addAll(groups);
    }

    public void removeAdmins(@NotNull final Collection<? extends GroupImpl> groups) {
        admins.removeAll(groups);
    }

    public void addAdmin(@NotNull final GroupImpl group) {
        admins.add(group);
    }

    public void removeAdmin(@NotNull final GroupImpl group) {
        admins.remove(group);
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
