package sn.sonatel.dsi.dif.om.keycloakapi.service;
import sn.sonatel.dsi.dif.om.keycloakapi.model.AccessTokenRequest;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationRequest;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationResponse;
import sn.sonatel.dsi.dif.om.keycloakapi.model.AccessTokenResponse;

public interface KeycloakService {

	RegistrationResponse createUser(RegistrationRequest userRequest);
	
	AccessTokenResponse getAccessToken(String msidn);
}
