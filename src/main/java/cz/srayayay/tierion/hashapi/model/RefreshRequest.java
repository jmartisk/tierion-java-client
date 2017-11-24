package cz.srayayay.tierion.hashapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshRequest {

    @JsonProperty
    private String refreshToken;

    public RefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
