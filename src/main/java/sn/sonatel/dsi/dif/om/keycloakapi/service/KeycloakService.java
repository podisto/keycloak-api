package sn.sonatel.dsi.dif.om.keycloakapi.service;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationRequest;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationResponse;
import sn.sonatel.dsi.dif.om.keycloakapi.model.TokenResponse;

public interface KeycloakService {

	RegistrationResponse createUser(RegistrationRequest userRequest);
	
	TokenResponse getAccessToken(String msidn);
}
