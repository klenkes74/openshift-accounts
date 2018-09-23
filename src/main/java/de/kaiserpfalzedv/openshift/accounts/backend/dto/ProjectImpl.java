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

package de.kaiserpfalzedv.openshift.accounts.backend.dto;

import de.kaiserpfalzedv.openshift.accounts.backend.dto.base.BaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Cluster;
import de.kaiserpfalzedv.openshift.accounts.backend.model.Project;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class ProjectImpl extends BaseEntity implements de.kaiserpfalzedv.openshift.accounts.backend.model.Project {
    private Cluster cluster;
    private String name;

    private AccountImpl owner;

    private final Set<GroupImpl> viewers = new HashSet<>();
    private final Set<GroupImpl> editors = new HashSet<>();
    private final Set<GroupImpl> admins = new HashSet<>();


    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public ProjectImpl() {}

    /**
     * Created the new account.
     * @param name The name of the project.
     * @param owner The owner of the project.
     */
    public ProjectImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,

            @NotNull final ClusterImpl cluster,
            @NotNull final String name,
            @NotNull final AccountImpl owner
    ) {
        super(id, version, Project.DEFAULT_TENANT, created, modified);

        setCluster(cluster);
        setName(name);
        setOwner(owner);
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
