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
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Project;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Entity(name = "Projects")
@Table(
        name = "PROJECTS"
)
public class JPAProject extends JPABaseEntity {
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
            name = "PROJECTS_VIEWERS",
            joinColumns = { @JoinColumn(name = "PROJECT_", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GROUP_", nullable = false) }
    )
    private Set<JPAGroup> viewers = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "PROJECTS_EDITORS",
            joinColumns = { @JoinColumn(name = "PROJECT_", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GROUP_", nullable = false) }
    )
    private Set<JPAGroup> editors = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "PROJECTS_ADMINS",
            joinColumns = { @JoinColumn(name = "PROJECT_", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GROUP_", nullable = false) }
    )
    private Set<JPAGroup> admins = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public JPAProject() {}

    /**
     * Created the new account.
     * @param name The name of the project.
     * @param owner The owner of the project.
     */
    public JPAProject(
            @NotNull final String name,
            @NotNull final JPAAccount owner,
            @NotNull final Collection<? extends JPAGroup> viewers,
            @NotNull final Collection<? extends JPAGroup> editors,
            @NotNull final Collection<? extends JPAGroup> admins
    ) {
        setName(name);
        setOwner(owner);

        setViewers(viewers);
        setEditors(editors);
        setAdmins(admins);
    }

    public JPAProject(
            @NotNull final UUID id, @NotNull final Long version,
            @NotNull final OffsetDateTime created, @NotNull final OffsetDateTime modified,
            @NotNull final String name, @NotNull final JPAAccount owner,
            @NotNull final Collection<? extends JPAGroup> viewers,
            @NotNull final Collection<? extends JPAGroup> editors,
            @NotNull final Collection<? extends JPAGroup> admins
    ) {
        super(id, version, created, modified);

        setName(name);
        setOwner(owner);

        setViewers(viewers);
        setEditors(editors);
        setAdmins(admins);
    }

    public JPAProject(@NotNull final Project orig) {
        this(orig.getId(), orig.getVersion(),
                orig.getCreated(), orig.getModified(),
                orig.getName(), new JPAAccount(orig.getOwner()),
                convertGroups(orig.getViewers()), convertGroups(orig.getEditors()), convertGroups(orig.getAdmins()));
    }

    private static Collection<? extends JPAGroup> convertGroups(Collection<? extends Group> groups) {
        HashSet<JPAGroup> result = new HashSet<>(groups.size());

        groups.forEach(g -> result.add(new JPAGroup(g)));

        return result;
    }


    public String getName() {
        return name;
    }

    private void setName(@NotNull final String name) {
        this.name = name;
    }


    public JPAAccount getOwner() {
        return owner;
    }

    public void setOwner(@NotNull final JPAAccount owner) {
        this.owner = owner;
    }


    public Set<JPAGroup> getViewers() {
        return viewers;
    }

    public void clearViewers() {
        viewers.clear();
    }

    public void setViewers(Collection<? extends JPAGroup> groups) {
        clearViewers();
        addViewers(groups);
    }

    public void addViewers(@NotNull final Collection<? extends JPAGroup> groups) {
        viewers.addAll(groups);
    }



    public void removeViewers(@NotNull final Collection<? extends JPAGroup> groups) {
        viewers.removeAll(groups);
    }

    public void addViewer(@NotNull final JPAGroup group) {
        viewers.add(group);
    }

    public void removeViewer(@NotNull final JPAGroup group) {
        viewers.remove(group);
    }


    public Set<JPAGroup> getEditors() {
        return editors;
    }

    public void clearEditors() {
        editors.clear();
    }

    public void setEditors(@NotNull final Collection<? extends JPAGroup> groups) {
        clearEditors();
        addEditors(groups);
    }

    public void addEditors(@NotNull final Collection<? extends JPAGroup> groups) {
        editors.addAll(groups);
    }

    public void removeEditors(@NotNull final Collection<? extends JPAGroup> groups) {
        editors.removeAll(groups);
    }

    public void addEditor(@NotNull final JPAGroup group) {
        editors.add(group);
    }

    public void removeEditor(@NotNull final JPAGroup group) {
        editors.remove(group);
    }


    public Set<JPAGroup> getAdmins() {
        return admins;
    }

    public void clearAdmins() {
        admins.clear();
    }

    public void setAdmins(@NotNull final Collection<? extends JPAGroup> groups) {
        clearAdmins();
        addAdmins(groups);
    }

    public void addAdmins(@NotNull final Collection<? extends JPAGroup> groups) {
        admins.addAll(groups);
    }

    public void removeAdmins(@NotNull final Collection<? extends JPAGroup> groups) {
        admins.removeAll(groups);
    }

    public void addAdmin(@NotNull final JPAGroup group) {
        admins.add(group);
    }

    public void removeAdmin(@NotNull final JPAGroup group) {
        admins.remove(group);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                JPAProject.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("owner='" + owner + "'")
                .add("name='" + name + "'")
                .toString();
    }
}
