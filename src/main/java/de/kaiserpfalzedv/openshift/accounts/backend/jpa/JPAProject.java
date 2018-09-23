/*
 * Copyright (c) 2018 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This file is part of openshift-accounts.
 *
 * openshift-accounts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.kaiserpfalzedv.openshift.accounts.backend.jpa;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Person;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.*;

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
