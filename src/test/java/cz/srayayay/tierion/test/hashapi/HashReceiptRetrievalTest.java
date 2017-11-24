package cz.srayayay.tierion.test.hashapi;

import org.junit.Assert;
import org.junit.Test;

import cz.srayayay.tierion.hashapi.TierionHashApiClient;
import cz.srayayay.tierion.hashapi.V1TierionHashApiClient;
import cz.srayayay.tierion.common.model.BlockchainReceipt;

public class HashReceiptRetrievalTest {

    @Test
    public void submitBytes() throws Exception {
        try(TierionHashApiClient client = new V1TierionHashApiClient()) {
            final BlockchainReceipt receipt = client.getReceipt("5a17c55a4613312f6366cb61");
            Assert.assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
                    receipt.getTargetHash());
            Assert.assertNotNull(receipt.getType());
            Assert.assertNotNull(receipt.getContext());
            Assert.assertNotNull(receipt.getMerkleRoot());
            Assert.assertTrue(receipt.getProof().size() > 0);
            Assert.assertTrue(receipt.getAnchors().size() > 0);
        }
    }

}
