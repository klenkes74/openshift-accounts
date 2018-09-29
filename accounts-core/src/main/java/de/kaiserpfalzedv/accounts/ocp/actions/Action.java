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

import java.util.Optional;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.accounts.ExecuterException;
import de.kaiserpfalzedv.accounts.ocp.OcpNameHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2017-09-14
 */
public abstract class Action<T extends OcpNameHolder> {
    protected static final Logger LOG = LoggerFactory.getLogger(Action.class);

    protected T data;
    protected ExecuteHttpCall sender;
    protected String localPart;


    protected Action(
            @NotNull final T data,
            @NotNull final String localPart,
            @NotNull final ExecuteHttpCall sender
    ) {
        this.data = data;
        this.localPart = localPart;
        this.sender = sender;
    }

    abstract public Optional<String> execute() throws ExecuterException;

    public T getData() {
        return data;
    }
}
