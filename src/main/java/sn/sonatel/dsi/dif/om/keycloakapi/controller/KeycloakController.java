package sn.sonatel.dsi.dif.om.keycloakapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationRequest;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationResponse;
import sn.sonatel.dsi.dif.om.keycloakapi.model.TokenResponse;
import sn.sonatel.dsi.dif.om.keycloakapi.service.KeycloakService;

@RestController
@RequestMapping("/keycloak")
@Slf4j
public class KeycloakController {
	
	private final KeycloakService keycloakService;
	
	public KeycloakController(KeycloakService keycloakService) {
		this.keycloakService = keycloakService;
	}
	
	@GetMapping("/token/{msidn}")
	public ResponseEntity<TokenResponse> getTokenUsingCredentials(@PathVariable("msidn") String msidn) {

		TokenResponse responseToken = null;
		try {
			responseToken = keycloakService.getAccessToken(msidn);
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
