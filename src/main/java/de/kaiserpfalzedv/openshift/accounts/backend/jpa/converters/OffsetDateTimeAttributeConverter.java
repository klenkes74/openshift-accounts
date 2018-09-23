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