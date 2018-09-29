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
import de.kaiserpfalzedv.accounts.templating.YamlPrinter;
import freemarker.template.TemplateException;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2017-09-14
 */
public class Create<T extends OcpNameHolder> extends Action<T> {
    private YamlPrinter printer;
    private String templateName;

    public Create(
            @NotNull T data,
            @NotNull String localPart,
            @NotNull ExecuteHttpCall sender,
            @NotNull YamlPrinter printer,
            @NotNull String templateName
    ) {
        super(data, localPart, sender);

        this.printer = printer;
        this.templateName = templateName;
    }

    @Override
    public Optional<String> execute() throws ExecuterException {
        LOG.info("Creating: {}", data.getOcpName());

        try {
            String body = printer.print(data, templateName);

            return sender.post(localPart, body);
        } catch (IOException | TemplateException e) {
            throw new ExecuterException("Could not generate creation request for group: " + data.getOcpName(), e);
        }
    }
}
