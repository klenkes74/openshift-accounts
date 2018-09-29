package de.kaiserpfalzedv.accounts.ocp;

import com.openshift.restclient.ClientBuilder;
import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IProject;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-29
 */
@Named
public class ProjectService {
    private IClient client;

    @Inject
    public ProjectService(final IClient client) {
        this.client = client;
    }


    public Set<IProject> getProjects(@NotNull final String user) {
        client.get
    }
}
