/*
 * Copyright (C) 2018 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.kaiserpfalzedv.accounts.ocp.actions;

import de.kaiserpfalzedv.accounts.ExecuterException;
import de.kaiserpfalzedv.accounts.ocp.OcpNameHolder;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2017-09-14
 */
public class Retrieve<T extends OcpNameHolder> extends Action<T> {

    public Retrieve(
            @NotNull T data,
            @NotNull String localPart,
            @NotNull ExecuteHttpCall sender
    ) {
        super(data, localPart, sender);
    }

    @Override
    public Optional<String> execute() throws ExecuterException {
        LOG.info("Retrieving: {}", data.getOcpName());

        return sender.get(localPart + data.getOcpName());
    }
}
