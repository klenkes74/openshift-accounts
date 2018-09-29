package de.kaiserpfalzedv.accounts.ocp.actions;

import de.kaiserpfalzedv.accounts.groups.OcpNameHolder;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2017-09-14
 */
public class Delete<T extends OcpNameHolder> extends Action<T> {
    public Delete(
            @NotNull final T data,
            @NotNull final String localPart,
            @NotNull final ExecuteHttpCall sender) {
        super(data, localPart, sender);
    }

    @Override
    public Optional<String> execute() {
        LOG.info("Deleting: {}", data.getOcpName());

        return sender.delete(localPart + data.getOcpName());
    }
}
