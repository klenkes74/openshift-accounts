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

package de.kaiserpfalzedv.openshift.accounts.backend.model.base;

import javax.validation.constraints.NotNull;

/**
 * The basic nameable. Every identity carrying a human readable name should implement this interface.
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-21
 */
public interface Nameable {
    /**
     * @return The full name of the entity.
     */
    public String getName();
    public void setName(@NotNull final String name);

    /**
     * @return A short representation of the name. Defaults to {@link #getName()}.
     */
    default String getShortName() {
        return getName();
    }
    default void setShortName(@NotNull final String name) {
        setName(name);
    }

    /**
     * @return The name for display on lists, titles, ...
     *  Defaults to {@link #getName()}.
     * entity.
     */
    default String getDisplayName() {
        return getName();
    }
    default void setDisplayName(@NotNull final String name) {
        setName(name);
    }
}
