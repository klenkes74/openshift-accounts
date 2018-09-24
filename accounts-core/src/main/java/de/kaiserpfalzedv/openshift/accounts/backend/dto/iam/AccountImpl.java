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

package de.kaiserpfalzedv.openshift.accounts.backend.dto.iam;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.dto.ocp.ProjectImpl;
import de.kaiserpfalzedv.openshift.accounts.backend.dto.base.BaseEntityImpl;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Account;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Person;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Project;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public class AccountImpl extends BaseEntityImpl implements Account {
    private String name;

    private PersonImpl owner;

    private final Set<ProjectImpl> projects = new HashSet<>();

    private final Set<GroupImpl> groups = new HashSet<>();

    /**
     * @deprecated Only for JPA ...
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    protected AccountImpl() {}


    /**
     * Created the new account.
     * @param id The ID of the account.
     * @param version The version of this account entry.
     * @param name The name of this account.
     * @param owner The owner of the account.
     */
    @SuppressWarnings({"unused", "WeakerAccess"})
    public AccountImpl(
            @NotNull final UUID id,
            final Long version,
            @NotNull final OffsetDateTime created,
            @NotNull final OffsetDateTime modified,
            @NotNull final String name,
            @NotNull final Person owner,
            @NotNull final Collection<? extends Project> projects,
            @NotNull final Collection<? extends Group> groups
    ) {
        super(id, version, created, modified);

        setName(name);
        setOwner(owner);

        setProjects(projects);
        setGroups(groups);
    }

    @SuppressWarnings("WeakerAccess")
    public AccountImpl(@NotNull final Account orig) {
        this(orig.getId(), orig.getVersion(), orig.getCreated(), orig.getModified(),
             orig.getName(), orig.getOwner(),
             orig.getProjects(), orig.getGroups());
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NotNull final String name) {
        this.name = name;
    }


    @Override
    public Person getOwner() {
        return owner;
    }

    public void setOwner(@NotNull final Person person) {
        this.owner = convertPerson(person);
    }

    private PersonImpl convertPerson(@NotNull final Person orig) {
        return (orig instanceof PersonImpl ? (PersonImpl) orig : new PersonImpl(orig));
    }


    public Set<? extends Project> getProjects() {
        return projects;
    }

    @Override
    public void setProjects(@NotNull Collection<? extends Project> projects) {
        clearProjects();
        addProjects(projects);
    }

    @Override
    public void clearProjects() {
        this.projects.clear();
    }

    @Override
    public void addProjects(@NotNull final Collection<? extends Project> projects) {
        this.projects.addAll(convertProjects(projects));
    }

    private Set<ProjectImpl> convertProjects(@NotNull final Collection<? extends Project> origs) {
        HashSet<ProjectImpl> result = new HashSet<>(origs.size());

        origs.forEach(p -> result.add(convertProject(p)));

        return result;
    }

    private ProjectImpl convertProject(@NotNull final Project orig) {
        return (orig instanceof ProjectImpl ? (ProjectImpl) orig : new ProjectImpl(orig));
    }

    @Override
    public void addProject(@NotNull final Project project) {
        this.projects.add(convertProject(project));
    }

    @Override
    public void removeProjects(@NotNull final Collection<? extends Project> projects) {
        this.projects.removeAll(convertProjects(projects));
    }

    @Override
    public void removeProject(@NotNull final Project project) {
        this.projects.remove(convertProject(project));
    }

    public Set<? extends Group> getGroups() {
        return groups;
    }

    public void clearGroups() {
        groups.clear();
    }

    public void setGroups(@NotNull final Collection<? extends Group> groups) {
        clearGroups();
        addGroups(groups);
    }

    public void addGroups(@NotNull final Collection<? extends Group> groups) {
        this.groups.addAll(convertGroups(groups));
    }

    private Set<GroupImpl> convertGroups(@NotNull final Collection<? extends Group> origs) {
        HashSet<GroupImpl> result = new HashSet<>(origs.size());

        origs.forEach(g -> result.add(convertGroup(g)));

        return result;
    }

    private static GroupImpl convertGroup(@NotNull final Group orig) {
        return (orig instanceof GroupImpl ? (GroupImpl) orig : new GroupImpl(orig));
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



    @Override
    public String toString() {
        return new StringJoiner(", ",
                AccountImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("version=" + getVersion())
                .add("owner='" + owner + "'")
                .add("projectsOwned=" + projects.size())
                .add("groupsOwned=" + groups.size())
                .toString();
    }
}
