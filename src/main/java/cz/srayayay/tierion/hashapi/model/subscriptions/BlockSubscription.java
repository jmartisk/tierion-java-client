package cz.srayayay.tierion.hashapi.model.subscriptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockSubscription {

    @JsonProperty
    private String id;

    @JsonProperty
    private String callbackUrl;

    @JsonProperty
    private String label;

    public BlockSubscription() {
    }

    public BlockSubscription(String callbackUrl, String label) {
        this.callbackUrl = callbackUrl;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getLabel() {
        return label;
    }
}
