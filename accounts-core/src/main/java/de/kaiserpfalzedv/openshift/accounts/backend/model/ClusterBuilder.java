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

package de.kaiserpfalzedv.openshift.accounts.backend.model;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.BaseEntityBuilder;
import javafx.util.Builder;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public abstract class ClusterBuilder<T extends Cluster> extends BaseEntityBuilder<T> implements Builder<T> {
    protected String name;
    protected final Set<Project> projects = new HashSet<>();


    public ClusterBuilder<T> setCluster(@NotNull Cluster cluster) {
        setIdentity(cluster);
        setChangeable(cluster);
        setVersionable(cluster);

        setName(cluster.getName());
        setProjects(cluster.getProjects());

        return this;
    }

    public ClusterBuilder<T> setName(@NotNull final String name) {
        this.name = name;
        return this;
    }

    public ClusterBuilder<T> setProjects(@NotNull final Collection<? extends Project> projects) {
        this.projects.clear();
        this.projects.addAll(projects);
        return this;
    }
}
