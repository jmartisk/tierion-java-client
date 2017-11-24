package cz.srayayay.tierion.dataapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srayayay.tierion.common.JsonHelper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatastoreObject {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String key;

    @JsonProperty
    private String name;

    @JsonProperty
    private String groupName;

    @JsonProperty
    private Boolean redirectEnabled;

    @JsonProperty
    private String redirectUrl;

    @JsonProperty
    private Boolean emailNotificationEnabled;

    @JsonProperty
    private String emailNotificationAddress;

    @JsonProperty
    private Boolean postDataEnabled;

    @JsonProperty
    private String postDataUrl;

    @JsonProperty
    private Boolean postReceiptEnabled;

    @JsonProperty
    private String postReceiptUrl;

    @JsonProperty
    private Long timestamp;

    public DatastoreObject() {

    }

    public DatastoreObject(String name, String groupName, Boolean redirectEnabled,
                           String redirectUrl, Boolean emailNotificationEnabled,
                           String emailNotificationAddress, Boolean postDataEnabled, String postDataUrl,
                           Boolean postReceiptEnabled, String postReceiptUrl) {
        this.name = name;
        this.groupName = groupName;
        this.redirectEnabled = redirectEnabled;
        this.redirectUrl = redirectUrl;
        this.emailNotificationEnabled = emailNotificationEnabled;
        this.emailNotificationAddress = emailNotificationAddress;
        this.postDataEnabled = postDataEnabled;
        this.postDataUrl = postDataUrl;
        this.postReceiptEnabled = postReceiptEnabled;
        this.postReceiptUrl = postReceiptUrl;
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }

    public Boolean getRedirectEnabled() {
        return redirectEnabled;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public Boolean getEmailNotificationEnabled() {
        return emailNotificationEnabled;
    }

    public String getEmailNotificationAddress() {
        return emailNotificationAddress;
    }

    public Boolean getPostDataEnabled() {
        return postDataEnabled;
    }

    public String getPostDataUrl() {
        return postDataUrl;
    }

    public Boolean getPostReceiptEnabled() {
        return postReceiptEnabled;
    }

    public String getPostReceiptUrl() {
        return postReceiptUrl;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setRedirectEnabled(Boolean redirectEnabled) {
        this.redirectEnabled = redirectEnabled;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setEmailNotificationEnabled(Boolean emailNotificationEnabled) {
        this.emailNotificationEnabled = emailNotificationEnabled;
    }

    public void setEmailNotificationAddress(String emailNotificationAddress) {
        this.emailNotificationAddress = emailNotificationAddress;
    }

    public void setPostDataEnabled(Boolean postDataEnabled) {
        this.postDataEnabled = postDataEnabled;
    }

    public void setPostDataUrl(String postDataUrl) {
        this.postDataUrl = postDataUrl;
    }

    public void setPostReceiptEnabled(Boolean postReceiptEnabled) {
        this.postReceiptEnabled = postReceiptEnabled;
    }

    public void setPostReceiptUrl(String postReceiptUrl) {
        this.postReceiptUrl = postReceiptUrl;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

}
