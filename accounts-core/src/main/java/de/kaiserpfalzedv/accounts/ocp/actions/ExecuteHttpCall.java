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

package de.kaiserpfalzedv.accounts.ocp.actions;

import de.kaiserpfalzedv.accounts.ExecuterException;
import de.kaiserpfalzedv.accounts.ocp.providers.OcpToken;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-09-10
 */
@Named
public class ExecuteHttpCall {
    private static final Logger LOG = LoggerFactory.getLogger(ExecuteHttpCall.class);


    private String masterUrl = "https://kubernetes.default.svc:443/api";

    @Inject
    private OcpToken token;

    private HttpRequestFactory requestFactory = new DefaultHttpRequestFactory();

    @Inject
    private HttpClient client;

    @Inject
    private HttpHost host;


    @PostConstruct
    public void init() {
        if ("-".equals(masterUrl)) {
            masterUrl = "https://"
                + System.getenv("OCP_MASTER_SERVICE_HOST");
        }
    }

    public Optional<String> get(@NotNull final String localPart) {
        try {
            return doRequest(host, requestFactory.newHttpRequest(HttpMethod.GET, localPart));
        } catch (MethodNotSupportedException e) {
            LOG.error(
                    "Request failed: server={}{}, method={} ({}. {})",
                    host, localPart, HttpGet.METHOD_NAME,
                    e.getClass().getSimpleName(), e.getMessage()
            );

            return Optional.empty();
        }
    }

    private Optional<String> doRequest(@NotNull final HttpHost host, @NotNull final HttpRequest request) {
        String result = null;

        try {
            LOG.debug("Connecting to: server={}{}, method={}", host, request.getRequestLine());

            HttpResponse response = client.execute(host, request);

            result = response.getEntity().getContent().toString();
        } catch (IOException e) {
            LOG.error(
                    "Request failed: server={}, line={} ({}. {})",
                    host, request.getRequestLine(),
                    e.getClass().getSimpleName(), e.getMessage()
            );

            return Optional.empty();
        }

        LOG.debug("Result: {}", result);
        return Optional.ofNullable(result);
    }

    public Optional<String> post(@NotNull final String localPart, @NotNull final String body) {
        try {
            HttpPost request = (HttpPost) requestFactory.newHttpRequest(HttpPost.METHOD_NAME, localPart);
            request.setEntity(new StringEntity(body));

            return doRequest(host, request);
        } catch (MethodNotSupportedException | UnsupportedEncodingException e) {
            LOG.error(
                    "Request failed: server={}{}, method={} ({}. {})",
                    host, localPart, HttpPost.METHOD_NAME,
                    e.getClass().getSimpleName(), e.getMessage()
            );
        }

        return Optional.empty();
    }

    public Optional<String> put(@NotNull final String localPart, @NotNull final String body) throws ExecuterException {
        try {
            HttpPut request = (HttpPut) requestFactory.newHttpRequest(HttpPut.METHOD_NAME, localPart);
            request.setEntity(new StringEntity(body));

            return doRequest(host, request);
        } catch (MethodNotSupportedException | UnsupportedEncodingException e) {
            LOG.error(
                    "Request failed: server={}{}, method={} ({}. {})",
                    host, localPart, HttpPut.METHOD_NAME,
                    e.getClass().getSimpleName(), e.getMessage()
            );
        }

        return Optional.empty();
    }


    public Optional<String> delete(@NotNull final String localPart) throws ExecuterException {
        try {
            return doRequest(host, requestFactory.newHttpRequest(HttpMethod.DELETE, localPart));
        } catch (MethodNotSupportedException e) {
            LOG.error(
                    "Request failed: server={}{}, method={} ({}. {})",
                    host, localPart, HttpDelete.METHOD_NAME,
                    e.getClass().getSimpleName(), e.getMessage()
            );

            return Optional.empty();
        }
    }
}
