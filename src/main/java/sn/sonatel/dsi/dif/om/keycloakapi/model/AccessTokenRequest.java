package sn.sonatel.dsi.dif.om.keycloakapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessTokenRequest {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}
