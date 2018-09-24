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
