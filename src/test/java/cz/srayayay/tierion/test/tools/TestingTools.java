package cz.srayayay.tierion.test.tools;

import java.util.concurrent.ThreadLocalRandom;

import junit.framework.AssertionFailedError;

import cz.srayayay.tierion.dataapi.TierionDataApiClient;
import cz.srayayay.tierion.dataapi.V1TierionDataApiClient;
import cz.srayayay.tierion.dataapi.model.DatastoreObject;
import cz.srayayay.tierion.dataapi.model.DatastoreObjectBuilder;

public class TestingTools {

    public static String generateRandomUrl() {
        return "http://" + ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
    }

    public static String generateRandomString(String prefix) {
        return prefix + ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
    }

    public static Long createADummyDatastore(TierionDataApiClient client) {
        final String name = generateRandomString("id");
        client.createDatastore(new DatastoreObjectBuilder().name(name).build());
        final DatastoreObject datastore = client.getAllDatastores()
                .stream()
                .filter(it -> it.getName().equals(name))
                .findAny()
                .orElseThrow(AssertionFailedError::new);
        return datastore.getId();
    }

    static class RemoveAllDatastores {
        public static void main(String[] args) throws Exception {
            try (TierionDataApiClient client = new V1TierionDataApiClient()) {
                client.getAllDatastores().forEach(datastore -> client.deleteDatastore(datastore.getId()));
            }
        }
    }

    static class CreateOneDatastore {
        public static void main(String[] args) throws Exception {
            try (TierionDataApiClient client = new V1TierionDataApiClient()) {
                client.createDatastore(new DatastoreObjectBuilder()
                        .name("test1datastore")
                        .build()
                );
            }
        }
    }


}
