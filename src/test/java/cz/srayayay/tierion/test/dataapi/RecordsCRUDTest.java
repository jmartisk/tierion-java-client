package cz.srayayay.tierion.test.dataapi;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.srayayay.tierion.dataapi.TierionDataApiClient;
import cz.srayayay.tierion.dataapi.V1TierionDataApiClient;
import cz.srayayay.tierion.dataapi.model.RecordObject;
import cz.srayayay.tierion.test.tools.TestingTools;

public class RecordsCRUDTest {

    private static TierionDataApiClient client;
    private static Long dummyDatastoreId;

    @BeforeClass
    public static void prepare() {
        client = new V1TierionDataApiClient();
        dummyDatastoreId = TestingTools.createADummyDatastore(client);
    }

    @AfterClass
    public static void cleanup() throws Exception {
        client.deleteDatastore(dummyDatastoreId);
        client.close();
    }

    @Test
    public void create_getAll_delete() throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("k1", "v1");
        data.put("k2", "v2");
        RecordObject returnedRecord = client.createRecord(dummyDatastoreId, data);
        final String recordId = returnedRecord.getId();
        Assert.assertNotNull(recordId);

        // FIXME the rest won't work until the record is processed
          /*  try {
                // read
                GetRecordsResponse allRecords = client.getRecords(TESTING_TEMPORARY_DATASTORE, 1L, 100L, null, null);
                final Optional<RecordObject> myRecord = allRecords.getRecords()
                        .stream()
                        .filter(record -> record.getId().equals(recordId))
                        .findFirst();

                final RecordObject actualRecord = myRecord.orElseThrow(NullPointerException::new);

                Assert.assertNotNull(actualRecord.getAccountId());
                Assert.assertNotNull(actualRecord.getStatus());
                Assert.assertEquals("v1", actualRecord.getData().get("k1"));
                Assert.assertEquals("v2", actualRecord.getData().get("k2"));
            } finally {
                // delete
                client.deleteRecord(recordId);

                // verify deletion
                try {
                    client.getRecord(recordId);
                    Assert.fail(
                            "Shouldn't be able to retrieve record id=" + recordId + " after it was deleted");
                } catch (TierionException ignored) {
                }
            }*/
    }

}
