package com.wolopolo.oauth2.dto.registeredclient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RegisteredClientResp {
    private String id;
    private String clientId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime clientIdIssuedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime clientSecretExpiresAt;
    private String clientName;
    private String clientAuthenticationMethods;
    private String authorizationGrantTypes;
    private String redirectUris;
    private String postLogoutRedirectUris;
    private String scopes;
    private long tokenTimeToLive;
}
