package cz.srayayay.tierion.hashapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
