package de.kaiserpfalzedv.accounts.ocp.actions;

import de.kaiserpfalzedv.accounts.ExecuterException;
import de.kaiserpfalzedv.accounts.groups.OcpNameHolder;

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
