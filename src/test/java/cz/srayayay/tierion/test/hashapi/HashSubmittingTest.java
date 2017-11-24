package cz.srayayay.tierion.test.hashapi;

import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import cz.srayayay.tierion.hashapi.TierionHashApiClient;
import cz.srayayay.tierion.hashapi.V1TierionHashApiClient;
import cz.srayayay.tierion.hashapi.model.HashResponse;

public class HashSubmittingTest {

    @Test
    public void submitBytes() throws Exception {
        try(TierionHashApiClient client = new V1TierionHashApiClient()) {
            final HashResponse response = client.submitContent("hello world!".getBytes());
            Assert.assertTrue(response.getTimestamp() != null);
            Assert.assertTrue(response.getReceiptId() != null);
        }
    }

    @Test
    public void submitHash() throws Exception {
        try(TierionHashApiClient client = new V1TierionHashApiClient()) {
            final HashResponse response = client.submitHash("7509e5bda0c762d2bac7f90d758b5b2263fa01ccbc542ab5e3df163be08e6ca9");
            Assert.assertTrue(response.getTimestamp() != null);
            Assert.assertTrue(response.getReceiptId() != null);
        }
    }

    @Test
    public void submitFile() throws Exception {
        try(TierionHashApiClient client = new V1TierionHashApiClient()) {
            final HashResponse response = client.submitFile(
                    Paths.get(ClassLoader.getSystemResource("example-content.txt").toURI())
            );
            Assert.assertTrue(response.getTimestamp() != null);
            Assert.assertTrue(response.getReceiptId() != null);
        }
    }

}
