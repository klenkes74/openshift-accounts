/*
 *    Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.accounts.ocp.providers;

import com.openshift.restclient.ClientBuilder;
import com.openshift.restclient.IClient;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-09-10
 */
@Named
public class HttpConfigurationFactory {
    private static final Logger LOG = LoggerFactory.getLogger(HttpConfigurationFactory.class);

    private static final String USER_AGENT = "KP-OCP-ACCOUNTS";

    private static final String HOST = "kubernetes.default.svc";
    private static final int PORT = 443;
    private static final String SCHEME = "https";

    private IClient client;

    @Produces
    public IClient clientBuilder(@NotNull final OcpToken token) {
        if (client != null) {
            client = new ClientBuilder(SCHEME + "://" + HOST + ":" + PORT)
                    .usingUserAgentPrefix("kp-ocp-accounts/")
                    .usingToken(token.getToken())
                    .build();
        }

        return client;
    }

    /**
     * Creates the http client to be used.
     *
     * @param headers The headers to be set. Will use {@link #defaultHeaders(OcpToken)} by default.
     * @return the http client to be used for connections to the server.
     */
    @Produces
    public HttpClient createClient(final Collection<? extends Header> headers) {
        HttpClientBuilder builder = HttpClientBuilder.create()
                .setUserAgent(USER_AGENT)
                .setDefaultHeaders(headers);

        return builder.build();
    }

    /**
     * Creates the default header for every request. Basically we configure the authorization, YAML as content-type,
     * JSON as result type and closing the connection after the request.
     *
     * @param token The authorization token.
     * @return a set of headers for every request.
     */
    @Produces
    public Set<Header> defaultHeaders(final OcpToken token) {
        HashSet<Header> result = new HashSet<>(2);

        result.add(new BasicHeader("Authorization", "Bearer " + token.getToken()));
        result.add(new BasicHeader("Accept", "application/json"));
        result.add(new BasicHeader("Content-Type", "application/yaml"));
        result.add(new BasicHeader("Connection", "close"));

        return result;
    }

    /**
     * The default OCP master to connect to.
     *
     * @return The OCP master to connect to.
     */
    @Produces
    public HttpHost defaultKubernetesHost() {
        HttpHost result = new HttpHost(HOST, PORT, SCHEME);

        return result;
    }
}
