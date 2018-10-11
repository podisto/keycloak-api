package sn.sonatel.dsi.dif.om.keycloakapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import sn.sonatel.dsi.dif.om.keycloakapi.model.AccessTokenRequest;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationRequest;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationResponse;
import sn.sonatel.dsi.dif.om.keycloakapi.model.AccessTokenResponse;
import sn.sonatel.dsi.dif.om.keycloakapi.service.KeycloakService;

@RestController
@RequestMapping("/keycloak")
@Slf4j
public class KeycloakController {
	
	private final KeycloakService keycloakService;
	
	public KeycloakController(KeycloakService keycloakService) {
		this.keycloakService = keycloakService;
	}
	
	@PostMapping("/token")
	public ResponseEntity<AccessTokenResponse> getTokenUsingCredentials(@RequestBody AccessTokenRequest request) {

		AccessTokenResponse responseToken = null;
		try {
			responseToken = keycloakService.getAccessToken(request.getUsername());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(responseToken);

	}
	
	@PostMapping("/registerClient")
	public ResponseEntity<RegistrationResponse> createUser(@RequestBody RegistrationRequest request) {
		try {
			RegistrationResponse response = keycloakService.createUser(request);
			return ResponseEntity.ok(response);
		}
		catch (Exception ex) {
			log.trace("exception caught {}", ex);
			return ResponseEntity.badRequest().build();
		}
	}

}
