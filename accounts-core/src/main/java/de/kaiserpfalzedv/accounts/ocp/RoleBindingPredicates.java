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

import java.util.Set;
import java.util.function.Predicate;

import javax.validation.constraints.NotNull;

import com.openshift.restclient.IClient;
import com.openshift.restclient.model.authorization.IRoleBinding;
import de.kaiserpfalzedv.accounts.ocp.providers.HttpConfigurationFactory;
import de.kaiserpfalzedv.accounts.ocp.providers.OcpSecretProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2018-10-01
 */
class RoleBindingPredicates {
    private static final IClient client = new HttpConfigurationFactory().clientBuilder(new OcpSecretProvider().getOcpToken().orElseThrow(IllegalStateException::new));


    public static Predicate<IRoleBinding> isAdmin() {
        return p -> "admin".equals(p.getRoleRef().getName());
    }

    public static Predicate<IRoleBinding> isUser(@NotNull final String userName) {
        return p -> p.getUserNames().contains(userName);
    }

    public static Predicate<IRoleBinding> isMemberOfGroups(@NotNull final String userName, @NotNull final Set<String> groupNames) {
        groupNames.forEach(n -> {
            client.get("group", )
        });
        return p -> p.
    }
}
