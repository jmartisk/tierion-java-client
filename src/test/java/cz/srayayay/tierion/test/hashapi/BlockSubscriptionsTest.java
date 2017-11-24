package cz.srayayay.tierion.test.hashapi;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.junit.Assert;
import org.junit.Test;

import cz.srayayay.tierion.common.TierionException;
import cz.srayayay.tierion.hashapi.TierionHashApiClient;
import cz.srayayay.tierion.hashapi.V1TierionHashApiClient;
import cz.srayayay.tierion.hashapi.model.subscriptions.BlockSubscription;

import static cz.srayayay.tierion.test.tools.TestingTools.generateRandomString;
import static cz.srayayay.tierion.test.tools.TestingTools.generateRandomUrl;

public class BlockSubscriptionsTest {

    @Test
    public void create_getAll_remove_get() throws Exception {
        try (TierionHashApiClient client = new V1TierionHashApiClient()) {
            // create a subscription
            String label1 = generateRandomString("id");
            String url1 = generateRandomUrl();
            String id1 = client.createBlockSubscription(url1, label1);

            try {
                // use getAllBlockSubscriptions for validating that the subscription exists
                final List<BlockSubscription> allBlockSubscriptions = client.getAllBlockSubscriptions();
                final BlockSubscription subscription1 = allBlockSubscriptions
                        .stream()
                        .filter(sub -> sub.getId().equals(id1))
                        .findAny()
                        .orElseThrow(AssertionFailedError::new);
                Assert.assertEquals(url1, subscription1.getCallbackUrl());
                Assert.assertEquals(label1, subscription1.getLabel());
            } finally {
                // delete the blockSubscription
                client.deleteBlockSubscription(id1);

                // verify that it was really deleted
                try {
                    client.getBlockSubscription(id1);
                    Assert.fail("Getting block subscription with id " + id1
                            + " should fail because the subscription was deleted");
                } catch (TierionException ex) {
                    // ok
                }
            }
        }
    }

    @Test
    public void create_update_get_delete() throws Exception {
        try (TierionHashApiClient client = new V1TierionHashApiClient()) {
            // create a subscription
            String label1 = generateRandomString("id");
            String url1 = generateRandomUrl();
            String id1 = client.createBlockSubscription(url1, label1);

            try {
                String label2 = generateRandomString("id");
                String url2 = generateRandomUrl();
                client.updateBlockSubscription(id1, url2, label2);

                final BlockSubscription updated = client.getBlockSubscription(id1);
                Assert.assertEquals(url2, updated.getCallbackUrl());
                Assert.assertEquals(label2, updated.getLabel());

            } finally {
                client.deleteBlockSubscription(id1);
                // verify that it was really deleted
                try {
                    client.getBlockSubscription(id1);
                    Assert.fail("Getting block subscription with id " + id1
                            + " should fail because the subscription was deleted");
                } catch (TierionException ex) {
                    // ok
                }
            }
        }
    }


}
