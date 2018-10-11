package sn.sonatel.dsi.dif.om.keycloakapi.service;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationRequest;

public interface KeycloakService {

	Integer createUser(RegistrationRequest userRequest);
	
	String getAccessToken(String msidn);
}
