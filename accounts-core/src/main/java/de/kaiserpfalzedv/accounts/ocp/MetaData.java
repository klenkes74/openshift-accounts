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

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-29
 */
public class MetaData implements OcpNameHolder, Serializable {
    private String name;
    private String clusterName;
    private String nameSpace;
    private String generatedName;

    private final HashSet<String> annotations = new HashSet<>();
    private final HashSet<String> labels = new HashSet<>();

    private OwnerReferences ownerReferences;

    private OffsetDateTime creationTimestamp;
    private int generation;
    private int resourceVersion;
    private String selfLink;
    private UUID uid;

    private int deletionGracePeriodSeconds;
    private OffsetDateTime deletionTimestamp;

    private final HashSet<Initializers> initializers = new HashSet<>();
    private final HashSet<String> finalizers = new HashSet<>();




    @Override
    public String getOcpName() {
        return name;
    }

    public class Initializers {

    }

    public class InitializerResults {

    }

    public class OwnerReferences {

    }

    public class MimimumMetaData {

    }
}
