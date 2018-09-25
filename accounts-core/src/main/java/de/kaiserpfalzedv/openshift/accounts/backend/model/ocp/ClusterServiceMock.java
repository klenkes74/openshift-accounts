/*
 *    Copyright 2018 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.kaiserpfalzedv.openshift.accounts.backend.model.ocp;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.ocp.JPACluster;
import de.kaiserpfalzedv.openshift.base.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2018-09-24
 */
@Mock
public class ClusterServiceMock implements ClusterService {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterServiceMock.class);

    private static final HashMap<UUID, JPACluster> clustersById = new HashMap<>();
    private static final HashMap<String, JPACluster> clustersByName = new HashMap<>();

    @PostConstruct
    public void init() {}

    @PreDestroy
    public void close() {}


    @Override
    public Cluster addCluster(@NotNull final String name) throws ClusterWithNameAlreadyExistsException {
        JPACluster result = new JPACluster(name);

        if (! clustersByName.containsKey(name)) {
            clustersById.put(result.getId(), result);
            clustersByName.put(result.getName(), result);
        } else {
            throw new ClusterWithNameAlreadyExistsException(name);
        }

        LOG.info("Saved: {}", result);
        return result;
    }

    @Override
    public Optional<Cluster> getCluster(@NotNull final UUID id) {
        if (! clustersById.containsKey(id))
            return Optional.empty();

        return Optional.of(clustersById.get(id));
    }

    @Override
    public Optional<Cluster> getCluster(@NotNull final String name) {
        if (! clustersByName.containsKey(name))
            return Optional.empty();

        return Optional.of(clustersByName.get(name));
    }
}
