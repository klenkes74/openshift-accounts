package de.kaiserpfalzedv.accounts.ocp;

import de.kaiserpfalzedv.accounts.groups.OcpNameHolder;

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
