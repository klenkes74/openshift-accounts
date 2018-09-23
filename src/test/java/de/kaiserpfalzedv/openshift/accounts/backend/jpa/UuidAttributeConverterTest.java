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

package de.kaiserpfalzedv.openshift.accounts.backend.jpa;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.converters.UuidAttributeConverter;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Test
public class UuidAttributeConverterTest {
    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final String DEFAULT_SQL_UID = DEFAULT_UUID.toString();

    private UuidAttributeConverter sut = new UuidAttributeConverter();

    public void shouldConvertTheUuidToSQL() {
        String result = sut.convertToDatabaseColumn(DEFAULT_UUID);

        assertEquals(result, DEFAULT_SQL_UID, "Converting to sql failed!");
    }

    public void shouldConvertSQLToUuid() {
        UUID result = sut.convertToEntityAttribute(DEFAULT_SQL_UID);

        assertEquals(result, DEFAULT_UUID, "Converting from sql failed!");
    }
}
