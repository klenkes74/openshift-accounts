package de.kaiserpfalzedv.accounts;

import de.kaiserpfalzedv.accounts.groups.Group;

import java.util.Map;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2017-09-12
 */
public interface GroupExecutor {
    void execute(Map<String, Group> ocpGroupNames, Map<String, Group> ldapGroups);
}
