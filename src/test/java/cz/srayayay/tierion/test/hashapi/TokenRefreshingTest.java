package cz.srayayay.tierion.test.hashapi;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import cz.srayayay.tierion.hashapi.TierionHashApiClient;
import cz.srayayay.tierion.hashapi.V1TierionHashApiClient;
import cz.srayayay.tierion.hashapi.model.HashResponse;

/**
 * Verify that refreshing the security token does not break the functionality of the client.
 */
public class TokenRefreshingTest {

    /**
     * Use a bit of a hack to trigger security token refresh earlier than it
     * would normally occur (which is after one hour). Check that the client will still work after that.
     */
    @Test
    public void testRefreshing() throws Exception {
        try(TierionHashApiClient client = new V1TierionHashApiClient()) {
            HashResponse response = client.submitContent("hello world!".getBytes());
            Assert.assertTrue(response.getTimestamp() != null);
            Assert.assertTrue(response.getReceiptId() != null);

            Field runnableField = V1TierionHashApiClient.class.getDeclaredField("refreshTokenTask");
            try {
                runnableField.setAccessible(true);
                ((Runnable)runnableField.get(client)).run();

                response = client.submitContent("hello world!2".getBytes());
                Assert.assertTrue(response.getTimestamp() != null);
                Assert.assertTrue(response.getReceiptId() != null);
            } finally {
                runnableField.setAccessible(false);
            }
        }
    }

}
