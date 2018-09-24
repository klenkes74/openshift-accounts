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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.base.JPABaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.ocp.JPAProject;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Account;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Person;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Project;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Entity(name = "Accounts")
@Table(
        name = "ACCOUNTS"
)
public class JPAAccount extends JPABaseEntity implements Account {
    @Column(name = "NAME_", nullable = false)
    private String name;

    @Column(name = "OWNER_", nullable = false)
    private JPAPerson owner;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<JPAProject> projects = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<JPAGroup> groups = new HashSet<>();

    /**
     * @deprecated Only for JPA ...
     */
    @Deprecated
    public JPAAccount() {super();}

    /**
     * Created the new account.
     * @param owner The owner of the account.
     */
    @SuppressWarnings("unused")
    public JPAAccount(
            @NotNull final UUID id,
            @NotNull final Long version,
            @NotNull final String name,
            @NotNull final Person owner
    ) {
        super(id, version);

        setName(name);
        setOwner(owner);
    }

    /**
     * Created the new account.
     * @param owner The owner of the account.
     */
    @SuppressWarnings("unused")
    public JPAAccount(
            @NotNull final UUID id,
            @NotNull final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final Person owner
    ) {
        super(id, version, created, modified);

        setName(name);
        setOwner(owner);
    }

    public JPAAccount(@NotNull final Account orig) {
        this(orig.getId(), orig.getVersion(),
                orig.getCreated(), orig.getModified(),
                orig.getName(), orig.getOwner());
    }


    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }


    public Person getOwner() {
        return owner;
    }

    @Override
    public void setOwner(@NotNull Person owner) {
        this.owner = (owner instanceof JPAPerson ? (JPAPerson) owner : new JPAPerson(owner));
    }


    public Set<? extends Group> getGroups() {
        return groups;
    }

    @Override
    public void setGroups(@NotNull final Collection<? extends Group> groups) {
        clearGroups();
        addGroups(groups);
    }

    public void clearGroups() {
        this.groups.clear();
    }

    public void addGroups(@NotNull final Collection<? extends Group> groups) {
        this.groups.addAll(convertGroups(groups));
    }

    private Collection<JPAGroup> convertGroups(@NotNull final Collection<? extends Group> groups) {
        HashSet<JPAGroup> result = new HashSet<>(groups.size());
        groups.forEach(g -> result.add(convertGroup(g)));
        return result;
    }

    private JPAGroup convertGroup(@NotNull final Group group) {
        return (group instanceof JPAGroup ? (JPAGroup) group : new JPAGroup(group));
    }

    public void addGroup(@NotNull final Group group) {
        this.groups.add(convertGroup(group));
    }

    public void removeGroups(@NotNull final Collection<? extends Group> groups) {
        this.groups.removeAll(convertGroups(groups));
    }

    public void removeGroup(@NotNull final Group group) {
        this.groups.remove(convertGroup(group));
    }


    public Set<? extends Project> getProjects() {
        return projects;
    }

    @Override
    public void setProjects(@NotNull Collection<? extends Project> projects) {
        clearProjects();
        addProjects(projects);
    }

    public void clearProjects() {
        this.projects.clear();
    }

    public void addProjects(@NotNull final Collection<? extends Project> projects) {
        this.projects.addAll(convertProjects(projects));
    }

    private Collection<JPAProject> convertProjects(@NotNull final Collection<? extends Project> projects) {
        HashSet<JPAProject> result = new HashSet<>(projects.size());
        projects.forEach(p -> result.add(convertProject(p)));
        return result;
    }

    private JPAProject convertProject(@NotNull final Project project) {
        return (project instanceof JPAProject ? (JPAProject) project : new JPAProject(project));
    }

    public void addProject(@NotNull final Project project) {
        this.projects.add(convertProject(project));
    }

    public void removeProjects(@NotNull final Collection<? extends Project> projects) {
        this.projects.removeAll(convertProjects(projects));
    }

    public void removeProject(@NotNull final Project project) {
        this.projects.remove(convertProject(project));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ",
                JPAAccount.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("version=" + getVersion())
                .add("created=" + getCreated())
                .add("modified=" + getModified())
                .add("name='" + name + "'")
                .add("owner='" + owner + "'")
                .add("projectsOwned=" + projects.size())
                .toString();
    }
}
