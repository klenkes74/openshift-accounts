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

package de.kaiserpfalzedv.openshift.accounts.backend.model;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.openshift.accounts.backend.jpa.ocp.JPACluster;
import de.kaiserpfalzedv.openshift.accounts.backend.model.ocp.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2018-09-24
 */
@Named
public class ClusterService implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterService.class);

    private EntityManager em;

    @Inject
    public ClusterService(@NotNull EntityManager em) {
        this.em = em;
    }

    @PostConstruct
    public void init() {}

    @PreDestroy
    public void close() {}


    public Cluster addCluster(@NotNull final String name) {
        JPACluster data = new JPACluster(name);

        em.persist(data);
        JPACluster result = em.find(JPACluster.class, data.getId());

        LOG.info("Saved: {}", result);
        return result;
    }
}
