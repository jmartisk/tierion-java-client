package cz.srayayay.tierion.dataapi.model;

/**
 * Programmatic builder for @{DatastoreObject}.
 * This is meant for client-side use, so it doesn't support properties which are usually generated on the server,
 * that is "id", "timestamp" and "key".
 */
public class DatastoreObjectBuilder {

    private String name;
    private String groupName;
    private Boolean redirectEnabled;
    private String redirectUrl;
    private Boolean emailNotificationEnabled;
    private String emailNotificationAddress;
    private Boolean postDataEnabled;
    private String postDataUrl;
    private Boolean postReceiptEnabled;
    private String postReceiptUrl;

    public DatastoreObjectBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DatastoreObjectBuilder groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public DatastoreObjectBuilder redirectEnabled(Boolean redirectEnabled) {
        this.redirectEnabled = redirectEnabled;
        return this;
    }

    public DatastoreObjectBuilder redirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public DatastoreObjectBuilder emailNotificationEnabled(Boolean emailNotificationEnabled) {
        this.emailNotificationEnabled = emailNotificationEnabled;
        return this;
    }

    public DatastoreObjectBuilder emailNotificationAddress(String emailNotificationAddress) {
        this.emailNotificationAddress = emailNotificationAddress;
        return this;
    }

    public DatastoreObjectBuilder postDataEnabled(Boolean postDataEnabled) {
        this.postDataEnabled = postDataEnabled;
        return this;
    }

    public DatastoreObjectBuilder postDataUrl(String postDataUrl) {
        this.postDataUrl = postDataUrl;
        return this;
    }

    public DatastoreObjectBuilder postReceiptEnabled(Boolean postReceiptEnabled) {
        this.postReceiptEnabled = postReceiptEnabled;
        return this;
    }

    public DatastoreObjectBuilder postReceiptUrl(String postReceiptUrl) {
        this.postReceiptUrl = postReceiptUrl;
        return this;
    }


    public DatastoreObject build() {
        // TODO some validity checks
        return new DatastoreObject(name, groupName, redirectEnabled, redirectUrl,
                emailNotificationEnabled, emailNotificationAddress, postDataEnabled, postDataUrl,
                postReceiptEnabled, postReceiptUrl);
    }
}