package com.wolopolo.oauth2.dto.registeredclient;

import com.wolopolo.oauth2.dto.OnCreate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredClientReq {
    @NotBlank
    @Pattern(regexp = "[\\w_]+")
    private String clientId;
    @NotBlank(groups = OnCreate.class)
    private String clientSecret;
//    private String clientAuthenticationMethod; // default CLIENT_SECRET_BASIC
//    private String authorizationGrantType; // default AUTHORIZATION_CODE
    private String redirectUris;
    private String scopes; // split by comma ,

    @Min(1)
    private long tokenTimeToLive = 300; // second
}
