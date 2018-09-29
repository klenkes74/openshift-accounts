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

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-09-10
 */
@Named
public class OcpSecretProvider {
    private static final Logger LOG = LoggerFactory.getLogger(OcpSecretProvider.class);


    private String tokenFile = "/var/run/secrets/kubernetes.io/serviceaccount/token";

    private String token = "-";


    @Produces
    public OcpToken getOcpToken() throws IOException {
        readTokenFromFileIfNotPresentedOnCommandLine();

        logToken(token);
        return new OcpToken(token);
    }

    private void logToken(String token) {
        if (! LOG.isTraceEnabled()) {
            token = "<token>";
        }

        LOG.info("OCP token to use: {}", token);
    }

    private void readTokenFromFileIfNotPresentedOnCommandLine() throws IOException {
        if ("-".equals(token)) {
            LOG.debug("Read OCP token from file: {}", tokenFile);

            try (FileInputStream inputStream = new FileInputStream(tokenFile)) {
                token = IOUtils.toString(inputStream);
            } catch(IOException e) {
                LOG.error(
                        "Can't read token from file '{}'. Reason: {} ({})",
                        tokenFile,
                        e.getMessage(),
                        e.getClass().getSimpleName()
                );

                throw new IllegalStateException(String.format("Can't read token from file '%s'", tokenFile), e);
            }
        }
    }
}
