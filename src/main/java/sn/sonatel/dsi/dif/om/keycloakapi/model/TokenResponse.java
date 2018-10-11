package sn.sonatel.dsi.dif.om.keycloakapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenResponse {
	
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("expires_in")
	private int expiresIn;
	@JsonProperty("refresh_expires_in")
	private int refreshExpiresIn;
	@JsonProperty("refresh_token")
	private String refreshToken;
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("not-before-policy")
	private boolean notBeforePolicy;
	@JsonProperty("session_state")
	private String sessionState;
	@JsonProperty("scope")
	private String scope;

}
