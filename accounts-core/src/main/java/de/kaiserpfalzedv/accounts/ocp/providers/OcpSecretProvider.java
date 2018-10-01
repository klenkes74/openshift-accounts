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

package de.kaiserpfalzedv.accounts.ocp.providers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public Optional<OcpToken> getOcpToken() {
        try {
            readTokenFromFileIfNotPresentedOnCommandLine();
        } catch (IOException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            return Optional.empty();
        }

        logToken(token);
        return Optional.of(new OcpToken(token));
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
