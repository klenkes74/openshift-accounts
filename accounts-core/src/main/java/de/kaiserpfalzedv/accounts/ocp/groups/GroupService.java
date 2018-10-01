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

import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.accounts.projects.Project;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2018-10-01
 */
public interface GroupService {
    static final GroupType VIEWER = GroupType.viewer;
    static final GroupType EDITOR = GroupType.editor;
    static final GroupType ADMIN = GroupType.admin;

    Set<Group> createGroupsForProject(@NotNull final Project project);
    Group createGroup(@NotNull final Project project, @NotNull final GroupType type);

    /**
     * Loads all groups and put them in a map with the group name as key.
     * @return Map of groups with the group name as key.
     */
    Map<String, Group> retrieveAll();
    Group retrieveByName(@NotNull final String name);
    Group updateGroup(@NotNull final Group group);
    void deleteGroup(@NotNull final String name);

    public enum GroupType {
        viewer,
        editor,
        admin;

        String groupName(@NotNull final Project project) {
            return project.getOcpName() + "-" + toString() + "s";
        }
    }
}
