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

package de.kaiserpfalzedv.openshift.accounts.backend.jpa.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-22
 */
@Converter(autoApply = true)
public class OffsetDateTimeAttributeConverter implements AttributeConverter<OffsetDateTime, Timestamp> {
    private static final Logger LOG = LoggerFactory.getLogger(OffsetDateTimeAttributeConverter.class);

    @Override
    public Timestamp convertToDatabaseColumn(OffsetDateTime attribute) {
        if (attribute != null) {
            checkArgument(attribute.getYear() >= 1, "Can't convert dates before year 1 AD!");
        }

        LOG.trace("Converting -> sql: {}", attribute);
        return (attribute == null ? null : Timestamp.valueOf(attribute.toLocalDateTime()));
    }

    @Override
    public OffsetDateTime convertToEntityAttribute(Timestamp sql) {
        LOG.trace("Converting -> attribute: {}", sql);

        return (sql == null ? null : sql.toLocalDateTime().atOffset(ZoneOffset.UTC));
    }
}