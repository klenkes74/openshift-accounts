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

package de.kaiserpfalzedv.openshift.accounts.backend.model.ocp;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Account;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Group;
import de.kaiserpfalzedv.openshift.accounts.backend.model.iam.Tenant;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-21
 */
public interface Project extends Tenant {
    public Cluster getCluster();
    public void setCluster(@NotNull final Cluster cluster);
    
    public Account getOwner();
    public void setOwner(@NotNull final Account owner);

    default UUID getTenant() {
        return getId();
    }
    
    public Set<? extends Group> getViewers();
    public void setViewers(@NotNull final Collection<? extends Group> groups);
    public void clearViewers();
    public void addViewers(@NotNull final Collection<? extends Group> groups);
    public void addViewer(@NotNull final Group group);
    public void removeViewers(@NotNull final Collection<? extends Group> groups);
    public void removeViewer(@NotNull final Group group);

    public Set<? extends Group> getEditors();
    public void setEditors(@NotNull final Collection<? extends Group> groups);
    public void clearEditors();
    public void addEditors(@NotNull final Collection<? extends Group> groups);
    public void addEditor(@NotNull final Group group);
    public void removeEditors(@NotNull final Collection<? extends Group> groups);
    public void removeEditor(@NotNull final Group group);

    public Set<? extends Group> getAdmins();
    public void setAdmins(@NotNull final Collection<? extends Group> groups);
    public void clearAdmins();
    public void addAdmins(@NotNull final Collection<? extends Group> groups);
    public void addAdmin(@NotNull final Group group);
    public void removeAdmins(@NotNull final Collection<? extends Group> groups);
    public void removeAdmin(@NotNull final Group group);
}
