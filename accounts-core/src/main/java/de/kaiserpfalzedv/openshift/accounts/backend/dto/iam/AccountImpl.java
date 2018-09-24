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
            @NotNull final PersonImpl owner,
            @NotNull final Collection<? extends ProjectImpl> projects,
            @NotNull final Collection<? extends GroupImpl> groups
    ) {
        super(id, version, Project.DEFAULT_TENANT, created, modified);

        setName(name);
        setOwner(owner);

        setProjects(projects);
        setGroups(groups);
    }

    @SuppressWarnings("WeakerAccess")
    public AccountImpl(@NotNull final Account orig) {
        this(orig.getId(), orig.getVersion(),
             orig.getCreated(), orig.getModified(),
             orig.getName(), convertPerson(orig.getOwner()),
             convertProjects(orig.getProjects()), convertGroups(orig.getGroups()));
    }

    private static PersonImpl convertPerson(@NotNull final Person orig) {
        return (orig instanceof PersonImpl ? (PersonImpl) orig : new PersonImpl(orig));
    }

    private static Set<ProjectImpl> convertProjects(@NotNull final Collection<? extends Project> origs) {
        HashSet<ProjectImpl> result = new HashSet<>(origs.size());

        origs.forEach(p -> result.add(convertProject(p)));

        return result;
    }

    private static ProjectImpl convertProject(@NotNull final Project orig) {
        return (orig instanceof ProjectImpl) ? (ProjectImpl) orig : new ProjectImpl(orig);
    }

    private static Set<GroupImpl> convertGroups(@NotNull final Collection<? extends Group> origs) {
        HashSet<GroupImpl> result = new HashSet<>(origs.size());

        origs.forEach((g -> result.add(convertGroup(g))));
        
        return result;
    }

    private static GroupImpl convertGroup(@NotNull final Group orig) {
        return (orig instanceof GroupImpl) ? (GroupImpl) orig : new GroupImpl(orig);
    }


    public String getName() {
        return name;
    }

    private void setName(@NotNull final String name) {
        this.name = name;
    }


    public Person getOwner() {
        return owner;
    }

    private void setOwner(@NotNull final PersonImpl person) {
        this.owner = person;
    }


    public Set<? extends Group> getGroups() {
        return groups;
    }

    private void setGroups(@NotNull final Collection<? extends GroupImpl> groups) {
        this.groups.clear();

        this.groups.addAll(groups);
    }


    public Set<? extends Project> getProjects() {
        return projects;
    }

    private void setProjects(@NotNull final Collection<? extends ProjectImpl> projects) {
        this.projects.clear();

        this.projects.addAll(projects);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ",
                AccountImpl.class.getSimpleName() + "@" + System.identityHashCode(this) + "[",
                "]")
                .add("id='" + getId().toString() + "'")
                .add("tenant='" + getTenant().toString() + "'")
                .add("owner='" + owner + "'")
                .add("projectsOwned=" + projects.size())
                .add("groupsOwned=" + groups.size())
                .toString();
    }
}
