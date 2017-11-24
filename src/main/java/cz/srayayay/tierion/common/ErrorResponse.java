package cz.srayayay.tierion.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an error response from the Tierion server.
 */
public class ErrorResponse {

    @JsonProperty
    private String error;

    public String getError() {
        return error;
    }

}
