package sn.sonatel.dsi.dif.om.keycloakapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RegistrationRequest {

	@JsonProperty("msidn")
	private String msidn;
	@JsonProperty("code_otp")
	private String codeOtp;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("os")
	private String os;
	@JsonProperty("channel")
	private String channel;
	@JsonProperty("firebase_id")
	private String firebaseId;
	@JsonProperty("app_version")
	private String appVersion;
	@JsonProperty("conf_version")
	private String confVersion;
	@JsonProperty("service_version")
	private String serviceVersion;
}
