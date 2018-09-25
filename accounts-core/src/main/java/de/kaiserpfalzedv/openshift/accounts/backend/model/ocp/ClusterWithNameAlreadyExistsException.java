package de.kaiserpfalzedv.openshift.accounts.backend.model.ocp;

import de.kaiserpfalzedv.openshift.accounts.backend.model.base.BaseException;

import javax.validation.constraints.NotNull;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-25
 */
public class ClusterWithNameAlreadyExistsException extends BaseException {
    public ClusterWithNameAlreadyExistsException(@NotNull final String name) {
        super("Cluster with name '" + name + "' already exists!");
    }
}
