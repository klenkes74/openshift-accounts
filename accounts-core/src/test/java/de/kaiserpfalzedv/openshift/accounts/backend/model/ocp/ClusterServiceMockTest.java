package de.kaiserpfalzedv.openshift.accounts.backend.model.ocp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2018-09-25
 */
@Test
public class ClusterServiceMockTest {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterServiceMockTest.class);


    private ClusterService sut = new ClusterServiceMock();

    public void shouldSaveANewCluster() throws ClusterWithNameAlreadyExistsException {
        Cluster data = sut.addCluster("wptl");

        assertEquals(data.getName(), "wptl", "Wrong cluster name returned!");
    }

    public void shouldRetrieveAClusterWhenTheCorrectNameIsGiven() throws ClusterWithNameAlreadyExistsException {
        Cluster data = sut.addCluster("wpt2");

        Optional<Cluster> optionalResult = sut.getCluster("wpt2");
        assert optionalResult.isPresent();
        Cluster result = optionalResult.get();

        assertEquals(result.getId(), data.getId(), "The Ids of the clusters don't match!");
        assertEquals(result.getName(), data.getName(), "The names of the clusters don't match");
    }

    @Test(expectedExceptions = ClusterWithNameAlreadyExistsException.class)
    public void shouldFailIfThereAreTwoClustersWithSameName() throws ClusterWithNameAlreadyExistsException {
        sut.addCluster("wpt3");
        sut.addCluster("wpt3");

        fail("The second creation of a cluster named 'wpt3' should have failed!");
    }
}
