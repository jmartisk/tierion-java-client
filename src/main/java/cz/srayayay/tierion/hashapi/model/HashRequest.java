package cz.srayayay.tierion.hashapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HashRequest {

	@JsonProperty
	private final String hash;

	public HashRequest(String hash) {
		this.hash = hash;
	}

	public String getHash() {
		return hash;
	}
}
