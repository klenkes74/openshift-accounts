/*
 * Copyright (c) 2018 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This file is part of openshift-accounts.
 *
 * openshift-accounts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.kaiserpfalzedv.openshift.accounts.backend.model;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Changeable;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Identifiable;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Nameable;
import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Versionable;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-21
 */
public interface Person extends Identifiable, Changeable, Versionable, Nameable {
    public void setName(final String name);

    public String getEmailAddress();
    public void setEmailAddress(final String emailAddress);

    default String getPreferredUserName() {
        return getName();
    }
}
