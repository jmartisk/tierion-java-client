package cz.srayayay.tierion.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jboss.resteasy.util.Hex;

public class Hashing {

    public static String hashFile(Path file) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            try (InputStream is = Files.newInputStream(file);
                 DigestInputStream dis = new DigestInputStream(is, md)) {
                readWholeInputStream(dis);
                return Hex.encodeHex(md.digest());
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Will read the input stream until its end (ignoring the contents), but will not close it.
     */
    private static void readWholeInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        while(inputStream.read(buffer) != -1) {
            // nothing
        }
    }

}
