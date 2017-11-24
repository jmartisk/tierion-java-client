package cz.srayayay.tierion.hashapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

	@JsonProperty(value = "access_token", required = true)
	private String accessToken;

	@JsonProperty("expires_in")
	private Long expiresIn;

	@JsonProperty(value = "refresh_token", required = true)
	private String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
}
