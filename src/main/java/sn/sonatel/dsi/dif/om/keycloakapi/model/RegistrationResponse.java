package sn.sonatel.dsi.dif.om.keycloakapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RegistrationResponse {

	/*@JsonProperty("act_app_vers")
	private String actAppVers;
	@JsonProperty("act_conf_vers")
	private String actConfVers;*/
	
	@JsonProperty("status_code")
	private String statusCode;
	
	/*@JsonProperty("status_wording")
	private String statusWording;
	@JsonProperty("nb_notif")
	private Integer nbNotif;
	@JsonProperty("conf_string")
	private String confString;
	@JsonProperty("content")
	private String content;*/ 
	
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("refresh_token")
	private String refreshToken;
	@JsonProperty("access_token_expiration_delay")
	private int accessTokenExpirationDelay;
}
