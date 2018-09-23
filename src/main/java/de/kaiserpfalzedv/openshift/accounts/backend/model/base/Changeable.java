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

package de.kaiserpfalzedv.openshift.accounts.backend.model.base;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
public interface Changeable {
    public static final ZoneId UTC = ZoneId.of("UTC");
    public static final OffsetDateTime MIN = OffsetDateTime.of(1, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
    public static final OffsetDateTime MAX = OffsetDateTime.of(99999, 12, 31, 23, 59, 59, 999999999, ZoneOffset.UTC);

    public OffsetDateTime getCreated();
    public OffsetDateTime getModified();
}
