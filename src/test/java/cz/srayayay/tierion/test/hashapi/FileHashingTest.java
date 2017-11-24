package cz.srayayay.tierion.test.hashapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import static cz.srayayay.tierion.common.Hashing.hashFile;

public class FileHashingTest {

    @Test
    public void testFileHashingUtility() throws IOException, URISyntaxException {
        Path exampleFile = Paths.get(ClassLoader.getSystemResource("example-content.txt").toURI());
        String hash = hashFile(exampleFile);
        Assert.assertEquals("096f3bcebfc82230c578c1db7b690a4ef07e3c2987b926ac203653eac768120a", hash);
    }

}
