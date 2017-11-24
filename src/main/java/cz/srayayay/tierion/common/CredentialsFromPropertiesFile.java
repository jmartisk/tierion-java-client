package cz.srayayay.tierion.common;

import java.io.IOException;
import java.util.Properties;

public class CredentialsFromPropertiesFile {

    private static String username;

    private static String password;

    private static String apiKey;

    static {
        Properties props = new Properties();
        try {
            props.load(ClassLoader.getSystemResourceAsStream("credentials.properties"));
            username = props.getProperty("username");
            password = props.getProperty("password");
            apiKey = props.getProperty("apiKey");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getApiKey() {
        return apiKey;
    }
}
