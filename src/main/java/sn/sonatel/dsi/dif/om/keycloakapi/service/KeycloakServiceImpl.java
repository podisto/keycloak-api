package sn.sonatel.dsi.dif.om.keycloakapi.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import sn.sonatel.dsi.dif.om.keycloakapi.model.RegistrationRequest;

@Service
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {

	@Value("${keycloak.credentials.secret}")
	private String SECRETKEY;

	@Value("${keycloak.resource}")
	private String CLIENTID;

	@Value("${keycloak.auth-server-url}")
	private String AUTHURL;

	@Value("${keycloak.realm}")
	private String REALM;

	@Override
	public String getAccessToken(String msidn) {
		String responseToken = null;
		try {
			responseToken = sendPost(this.buildParams(msidn));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseToken;
	}

	@Override
	public Integer createUser(RegistrationRequest userRequest) {
		int statusId = 0;
		try {
			UsersResource userRessource = getKeycloakUserResource();
			UserRepresentation user = new UserRepresentation();
			user.setUsername(userRequest.getMsidn());
			user.setAttributes(this.mapValues(userRequest));
			user.setEnabled(true);

			// Create user
			Response result = userRessource.create(user);
			log.info("Keycloak create user response code {} ", result.getStatus());

			statusId = result.getStatus();

			if (statusId == 201) {
				String userId = result.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
				log.info("User created with userId {} ", userId);

				// Define password credential
				CredentialRepresentation passwordCred = new CredentialRepresentation();
				passwordCred.setTemporary(false);
				passwordCred.setType(CredentialRepresentation.PASSWORD);
				passwordCred.setValue(userRequest.getMsidn()); // password

				// Set password credential
				userRessource.get(userId).resetPassword(passwordCred);
				log.info("Username {} created in keycloak successfully", userRequest.getMsidn());
			}

			else if (statusId == 409) {
				log.info("Username {} already present in keycloak", userRequest.getMsidn());
			} else {
				log.info("Username {} could not be created in keycloak", userRequest.getMsidn());
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return statusId;
	}

	private UsersResource getKeycloakUserResource() {

		Keycloak kc = KeycloakBuilder.builder().serverUrl(AUTHURL).realm("master").username("admin").password("admin")
				.clientId("admin-cli").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
				.build();

		RealmResource realmResource = kc.realm(REALM);
		UsersResource userRessource = realmResource.users();

		return userRessource;
	}

	private HashMap<String, List<String>> mapValues(RegistrationRequest userRequest) {
		HashMap<String, List<String>> attributes = new HashMap<>();
		// attributes.put("hashValue", Arrays.asList(userRequest.getHashValue()));
		// attributes.put("hashVersion", Arrays.asList(userRequest.getHashVersion()));
		return attributes;
	}

	private String sendPost(List<NameValuePair> urlParameters) throws Exception {

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(AUTHURL + "/realms/" + REALM + "/protocol/openid-connect/token");

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}

	private List<NameValuePair> buildParams(String msidn) {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
		urlParameters.add(new BasicNameValuePair("username", msidn));
		urlParameters.add(new BasicNameValuePair("password", msidn));
		urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));
		return urlParameters;

	}

}
