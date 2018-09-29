package de.kaiserpfalzedv.accounts.ocp.actions;

import de.kaiserpfalzedv.accounts.ExecuterException;
import de.kaiserpfalzedv.accounts.groups.OcpNameHolder;
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
