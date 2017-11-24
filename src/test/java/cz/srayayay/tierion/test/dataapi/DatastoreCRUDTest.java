package cz.srayayay.tierion.test.dataapi;

import junit.framework.AssertionFailedError;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.srayayay.tierion.common.TierionException;
import cz.srayayay.tierion.dataapi.TierionDataApiClient;
import cz.srayayay.tierion.dataapi.V1TierionDataApiClient;
import cz.srayayay.tierion.dataapi.model.DatastoreObject;
import cz.srayayay.tierion.dataapi.model.DatastoreObjectBuilder;
import cz.srayayay.tierion.test.tools.TestingTools;

public class DatastoreCRUDTest {

    private static TierionDataApiClient client;

    @BeforeClass
    public static void prepare() {
        client = new V1TierionDataApiClient();
    }

    @AfterClass
    public static void cleanup() throws Exception {
        client.close();
    }

    @Test
    public void create_delete() throws Exception {
        final String name = TestingTools.generateRandomString("id");
        final DatastoreObject createdObject = client.createDatastore(
                new DatastoreObjectBuilder()
                        .name(name)
                        .build()
        );

        Long id = null;
        try {
            // FIXME this doesn't work due to tierion bug, throws 500 on success
            Assert.assertNotNull("BUG", createdObject);
            id = createdObject.getId();
        } finally {
            if (id != null) {
                client.deleteDatastore(id);
                try {
                    client.getDatastore(id);
                    Assert.fail("Shouldn't be able to retrieve datastore id=" + id + " after deleting it");
                } catch (TierionException ignored) {
                }
            }
        }
    }

    @Test
    public void create_get_delete() throws Exception {
        final String name = TestingTools.generateRandomString("id");
        client.createDatastore(
                new DatastoreObjectBuilder()
                        .name(name)
                        .build()
        );

        Long id = null;
        try {
            final DatastoreObject datastore = client.getAllDatastores()
                    .stream()
                    .filter(it -> it.getName().equals(name))
                    .findAny()
                    .orElseThrow(AssertionFailedError::new);
            id = datastore.getId();
        } finally {
            if (id != null) {
                client.deleteDatastore(id);
                try {
                    client.getDatastore(id);
                    Assert.fail(
                            "Shouldn't be able to retrieve datastore id=" + id + " after deleting it");
                } catch (TierionException ignored) {
                }
            }
        }
    }

}
