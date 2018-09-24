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
