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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.ocp.JPACluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2018-09-24
 */
@Default
public class ClusterServiceImpl implements ClusterService {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterServiceImpl.class);

    private EntityManager em;

    @Inject
    public ClusterServiceImpl(@NotNull EntityManager em) {
        this.em = em;
    }

    @PostConstruct
    public void init() {}

    @PreDestroy
    public void close() {}


    @Override
    public Cluster addCluster(@NotNull final String name) {
        JPACluster data = new JPACluster(name);

        em.persist(data);
        JPACluster result = em.find(JPACluster.class, data.getId());

        LOG.info("Saved: {}", result);
        return result;
    }

    @Override
    public Optional<Cluster> getCluster(@NotNull UUID id) {
        return Optional.ofNullable(em.find(JPACluster.class, id));
    }

    @Override
    public Optional<Cluster> getCluster(@NotNull String name) {
        return Optional.ofNullable(
                em
                        .createQuery("select c from Clusters where name = :name", JPACluster.class)
                        .setParameter("name", name)
                        .getSingleResult()
        );
    }
}
