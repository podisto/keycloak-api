package sn.sonatel.dsi.dif.om.keycloakapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationRequest;
import sn.sonatel.dsi.dif.om.keycloakapi.service.KeycloakService;

@RestController
@RequestMapping("/keycloak")
public class KeycloakController {
	
	private final KeycloakService keycloakService;
	
	public KeycloakController(KeycloakService keycloakService) {
		this.keycloakService = keycloakService;
	}
	
	@PostMapping("/registerClient")
	public ResponseEntity<?> createUser(@RequestBody RegistrationRequest request) {
		try {
			int status = keycloakService.createUser(request);
			return ResponseEntity.ok(status);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().build();

		}
	}

}
