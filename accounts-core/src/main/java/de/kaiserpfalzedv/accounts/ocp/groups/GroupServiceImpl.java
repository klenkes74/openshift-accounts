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

package de.kaiserpfalzedv.accounts.ocp.groups;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.openshift.restclient.IClient;
import de.kaiserpfalzedv.accounts.projects.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2018-10-01
 */
@Named
public class GroupServiceImpl implements GroupService {
    private static final Logger LOG = LoggerFactory.getLogger(GroupServiceImpl.class);

    private IClient client;


    @Inject
    public GroupServiceImpl(@NotNull final IClient client) {
        this.client = client;
    }


    @Override
    public Set<Group> createGroupsForProject(@NotNull Project project) {
        HashSet<Group> result = new HashSet<>(3);

        result.add(createGroup(project, GroupType.viewer));
        result.add(createGroup(project, GroupType.editor));
        result.add(createGroup(project, GroupType.admin));

        return result;
    }

    @Override
    @CacheRemoveAll(cacheName = "all-groups")
    public Group createGroup(@NotNull Project project, @NotNull GroupType type) {

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.accounts.ocp.groups.GroupServiceImpl.createGroup
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @CacheResult(cacheName = "all-groups")
    public Map<String, Group> retrieveAll() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.accounts.ocp.groups.GroupServiceImpl.retrieveAll
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Group retrieveByName(@NotNull String name) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.accounts.ocp.groups.GroupServiceImpl.retrieveByName
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Group updateGroup(@NotNull Group group) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.accounts.ocp.groups.GroupServiceImpl.updateGroup
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteGroup(@NotNull String name) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.accounts.ocp.groups.GroupServiceImpl.deleteGroup
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
