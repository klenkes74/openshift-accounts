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

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.BaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Nameable;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-21
 */
public interface Cluster extends BaseEntity, Nameable {
    public Set<? extends Project> getProjects();
    public void setProjects(@NotNull final Collection<? extends Project> projects);
    public void clearProjects();
    public void addProjects(@NotNull final Collection<? extends Project> projects);
    public void addProject(@NotNull final Project project);
    public void removeProjects(@NotNull final Collection<? extends Project> projects);
    public void removeProject(@NotNull final Project project);
}
