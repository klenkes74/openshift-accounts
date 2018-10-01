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

package de.kaiserpfalzedv.accounts.ocp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Predicate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IProject;
import com.openshift.restclient.model.authorization.IRoleBinding;
import de.kaiserpfalzedv.accounts.projects.Project;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-29
 */
@Named
public class ProjectService {
    private IClient client;

    @Inject
    public ProjectService(final IClient client) {
        this.client = client;
    }


    public Set<Project> getProjects(@NotNull final String user) {
        HashSet<Project> results = new HashSet<>();

        List<IRoleBinding> roleBindings = client.list("RoleBinding");
        Set<IRoleBinding> userIsAdmin = new ConcurrentSkipListSet<>();

        OnlyUserOrGroup onlyUserOrGroup = new OnlyUserOrGroup(user);
        OnlyAdmin onlyAdmin = new OnlyAdmin(user);
        roleBindings.stream().filter(onlyUserOrGroup.and(onlyAdmin)).forEach(userIsAdmin::add);

        roleBindings.listIterator().next().getGroupNames()

        
    }
}


