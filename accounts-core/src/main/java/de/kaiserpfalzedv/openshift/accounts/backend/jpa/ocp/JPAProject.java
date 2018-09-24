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

package de.kaiserpfalzedv.openshift.accounts.backend.jpa.ocp;

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
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.iam.JPAAccount;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.iam.JPAGroup;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Account;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Cluster;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Project;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Entity(name = "Projects")
@Table(
        name = "PROJECTS"
)
public class JPAProject extends JPABaseEntity implements Project {
    private String name;
    private JPAAccount owner;
    private JPACluster cluster;

    private final HashSet<JPAGroup> viewers = new HashSet<>();
    private final HashSet<JPAGroup> editors = new HashSet<>();
    private final HashSet<JPAGroup> admins = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    protected JPAProject() {}

    /**
     * Created the new account.
     * @param name The name of the project.
     * @param owner The owner of the project.
     */
    @SuppressWarnings({"unused", "deprecation"})
    public JPAProject(
            @NotNull final String name,
            @NotNull final Account owner,
            @NotNull final Cluster cluster,
            @NotNull final Collection<? extends Group> viewers,
            @NotNull final Collection<? extends Group> editors,
            @NotNull final Collection<? extends Group> admins
    ) {
        setName(name);
        setOwner(owner);
        setCluster(cluster);

        setViewers(viewers);
        setEditors(editors);
        setAdmins(admins);
    }

    @SuppressWarnings("WeakerAccess")
    public JPAProject(
            @NotNull final UUID id, @NotNull final Long version,
            @NotNull final OffsetDateTime created, @NotNull final OffsetDateTime modified,
            @NotNull final String name, @NotNull final Account owner, @NotNull final Cluster cluster,
            @NotNull final Collection<? extends Group> viewers,
            @NotNull final Collection<? extends Group> editors,
            @NotNull final Collection<? extends Group> admins
    ) {
        super(id, version, created, modified);

        setName(name);
        setOwner(owner);
        setCluster(cluster);

        setViewers(viewers);
        setEditors(editors);
        setAdmins(admins);
    }

    public JPAProject(@NotNull final Project orig) {
        this(orig.getId(), orig.getVersion(),
                orig.getCreated(), orig.getModified(),
                orig.getName(), orig.getOwner(), orig.getCluster(),
                orig.getViewers(), orig.getEditors(), orig.getAdmins());
    }


    @Column(name = "NAME_", length = 100, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER_", nullable = false)
    public JPAAccount getOwner() {
        return owner;
    }

    public void setOwner(@NotNull final Account owner) {
        this.owner = (owner instanceof JPAAccount ? (JPAAccount) owner : new JPAAccount(owner));
    }


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "CLUSTER_", nullable = false)
    public JPACluster getCluster() {
        return cluster;
    }

    public void setCluster(@NotNull final Cluster cluster) {
        this.cluster = (cluster instanceof JPACluster ? (JPACluster) cluster : new JPACluster(cluster));
    }


    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "PROJECTS_VIEWERS",
            joinColumns = { @JoinColumn(name = "PROJECT_", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GROUP_", nullable = false) }
    )
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

    private static Collection<? extends JPAGroup> convertGroups(Collection<? extends Group> groups) {
        HashSet<JPAGroup> result = new HashSet<>(groups.size());

        groups.forEach(g -> result.add(convertGroup(g)));

        return result;
    }

    private static JPAGroup convertGroup(Group group) {
        return (group instanceof JPAGroup ? (JPAGroup) group : new JPAGroup(group));
    }

    public void removeViewers(@NotNull final Collection<? extends Group> groups) {
        viewers.removeAll(convertGroups(groups));
    }

    public void addViewer(@NotNull final Group group) {
        viewers.add(convertGroup(group));
    }

    public void removeViewer(@NotNull final Group group) {
        viewers.remove(convertGroup(group));
    }


    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "PROJECTS_EDITORS",
            joinColumns = { @JoinColumn(name = "PROJECT_", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GROUP_", nullable = false) }
    )
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

    public void removeEditors(@NotNull final Collection<? extends Group> groups) {
        editors.removeAll(convertGroups(groups));
    }

    public void addEditor(@NotNull final Group group) {
        editors.add(convertGroup(group));
    }

    public void removeEditor(@NotNull final Group group) {
        editors.remove(convertGroup(group));
    }


    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "PROJECTS_ADMINS",
            joinColumns = { @JoinColumn(name = "PROJECT_", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GROUP_", nullable = false) }
    )
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

    public void removeAdmins(@NotNull final Collection<? extends Group> groups) {
        admins.removeAll(convertGroups(groups));
    }

    public void addAdmin(@NotNull final Group group) {
        admins.add(convertGroup(group));
    }

    public void removeAdmin(@NotNull final Group group) {
        admins.remove(convertGroup(group));
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                JPAProject.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("version=" + getVersion())
                .add("created=" + getCreated())
                .add("modified=" + getModified())
                .add("owner='" + owner.getShortName() + "'")
                .add("name='" + name + "'")
                .add("viewerCount=" + viewers.size())
                .add("editorCount=" + editors.size())
                .add("adminCount=" + admins.size())
                .toString();
    }
}
