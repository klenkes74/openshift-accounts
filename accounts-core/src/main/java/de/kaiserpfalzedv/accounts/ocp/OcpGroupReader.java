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
                                                 
package de.kaiserpfalzedv.accounts.ocp;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzedv.accounts.ExecuterException;
import de.kaiserpfalzedv.accounts.ocp.actions.ExecuteHttpCall;
import de.kaiserpfalzedv.accounts.ocp.groups.Group;
import de.kaiserpfalzedv.accounts.ocp.groups.GroupBuilder;
import de.kaiserpfalzedv.accounts.users.User;
import de.kaiserpfalzedv.accounts.users.UserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-09-13
 */
@Named
public class OcpGroupReader {
    private static final Logger LOG = LoggerFactory.getLogger(OcpGroupReader.class);


    @Inject
    private ExecuteHttpCall sender;


    public Map<String, Group> execute() throws ExecuterException {
        HashMap<String, Group> result = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode items;
        try {
            Optional<String> ocpBody = sender.get(Group.LOCAL_PART);
            if (! ocpBody.isPresent()) {
                throw new ExecuterException("Can't retrieve groups from OCP master servers!");
            }

            items = mapper.readTree(ocpBody.get()).get("items");
        } catch (IOException e) {
            throw new ExecuterException("Can't read groups: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
        }

        items.elements().forEachRemaining(i -> {
            GroupBuilder builder = new GroupBuilder();

            JsonNode metadata = i.get("metadata");
            JsonNode annotations = metadata.get("annotations");
            JsonNode ocpUsers = i.get("users");

            builder.withOcpName(metadata.get("name").asText());

            try {
                builder.withResourceVersion(metadata.get("resourceVersion").asText());
            } catch (NullPointerException e) {
                // nothing to do. No resourceVersion is required!
            }

            try {
                builder.withUuid(metadata.get("uid").asText());
            } catch (NullPointerException e) {
                // nothing to do. No UID required.
            }

            try {
                builder.withDn(annotations.get("ldap-group").asText());
            } catch (NullPointerException e) {
                builder.withDn("-");
            }
            try {
                builder.withLdapServer(annotations.get("ldap-server").asText());
            } catch (NullPointerException e) {
                builder.withLdapServer("-");
            }
            try {
                OffsetDateTime syncDate = OffsetDateTime.parse(annotations.get("ldap-sync").asText());
                builder.withSyncDate(syncDate);
            } catch (NullPointerException | DateTimeParseException e) {
                // no sync time given. Will be replaced with NOW by the builder.
            }

            HashSet<User> users = new HashSet<>();
            ocpUsers.elements().forEachRemaining(u -> {
                User user = new UserBuilder()
                        .withUserName(u.asText())
                        .withDn(u.asText())
                        .build();
                users.add(user);
            });

            builder.addUsers(users);
            Group group = builder.build();
            result.put(group.getOcpName(), group);
        });

        LOG.info("OCP groups: {}", result.keySet());
        return result;
    }
}
