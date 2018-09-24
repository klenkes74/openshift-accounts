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

package de.kaiserpfalzedv.openshift.accounts.backend.model.iam;

import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.BaseEntity;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Nameable;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public interface Group extends BaseEntity, Nameable {
    public Account getOwner();
    public void setOwner(@NotNull final Account owner);

    public Set<? extends Account> getAccounts();
    public void setAccounts(@NotNull final Collection<? extends Account> projects);
    public void clearAccounts();
    public void addAccounts(@NotNull final Collection<? extends Account> projects);
    public void addAccount(@NotNull final Account project);
    public void removeAccounts(@NotNull final Collection<? extends Account> projects);
    public void removeAccount(@NotNull final Account project);
}
