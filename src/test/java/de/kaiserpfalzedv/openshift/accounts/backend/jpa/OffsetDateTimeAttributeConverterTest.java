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

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.Changeable;
import de.kaiserpfalzedv.openshift.accounts.backend.jpa.converters.OffsetDateTimeAttributeConverter;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.testng.Assert.assertEquals;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Test
public class OffsetDateTimeAttributeConverterTest {
    private OffsetDateTime DEFAULT_OFFSET_DATE_TIME = OffsetDateTime.of(2018, 9, 22, 10, 4, 0, 5, ZoneOffset.UTC);
    private Timestamp DEFAULT_TIMESTAMP = Timestamp.valueOf("2018-09-22 10:04:00.000000005");


    private OffsetDateTimeAttributeConverter sut = new OffsetDateTimeAttributeConverter();


    public void shouldConvertDefaultOffsetDateTimeCorrectly() {
        Timestamp result = sut.convertToDatabaseColumn(DEFAULT_OFFSET_DATE_TIME);

        assertEquals(result, DEFAULT_TIMESTAMP, "Conversion to timestamp failed!");
    }

    public void shouldConvertDefaultTimestampCorrectly() {
        OffsetDateTime result = sut.convertToEntityAttribute(DEFAULT_TIMESTAMP);

        assertEquals(result, DEFAULT_OFFSET_DATE_TIME, "Conversion from sql failed!");
    }

    public void shouldConvertCorrectlyWhenGivenChangeableMIN() {
        Timestamp converted = sut.convertToDatabaseColumn(Changeable.MIN);
        OffsetDateTime result = sut.convertToEntityAttribute(converted);

        assertEquals(result, Changeable.MIN, "Minimum time failed to convert!");
    }

    public void shouldConvertCorrectlyWhenGivenChangeableMAX() {
        Timestamp converted = sut.convertToDatabaseColumn(Changeable.MAX);
        OffsetDateTime result = sut.convertToEntityAttribute(converted);

        assertEquals(result, Changeable.MAX, "Maximum time failed to convert!");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Can't convert dates before year 1 AD!")
    public void shouldThrowExceptionWhenYearIsBeforeYear1() {
        sut.convertToDatabaseColumn(OffsetDateTime.MIN);
    }
}
