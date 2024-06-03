package com.wolopolo.oauth2.mapper;

import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientResp;
import com.wolopolo.oauth2.entity.Oauth2RegisteredClient;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;

import java.time.Duration;
import java.time.ZoneId;
import java.util.stream.Collectors;

public class RegisteredClientMapper {
    public static RegisteredClientResp convert(RegisteredClient registeredClient) {
        RegisteredClientResp resp = new RegisteredClientResp();
        resp.setId(registeredClient.getId());
        resp.setClientId(registeredClient.getClientId());
        resp.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt().atZone(ZoneId.systemDefault()).toLocalDateTime());
        resp.setClientName(registeredClient.getClientName());
        resp.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods().stream()
                .map(ClientAuthenticationMethod::toString)
                .collect(Collectors.joining()));
        resp.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes().stream()
                .map(AuthorizationGrantType::getValue)
                .collect(Collectors.joining()));
        resp.setRedirectUris(String.join(",", registeredClient.getRedirectUris()));
        resp.setPostLogoutRedirectUris(String.join(",", registeredClient.getPostLogoutRedirectUris()));
        resp.setScopes(String.join(",", registeredClient.getScopes()));
        Object tokenTimeToLive = registeredClient.getTokenSettings().getSetting(ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE);
        if(tokenTimeToLive instanceof Duration) {
            resp.setTokenTimeToLive(((Duration) tokenTimeToLive).getSeconds());
        }

        return resp;
    }

    public static RegisteredClientResp convert(Oauth2RegisteredClient registeredClient) {
        RegisteredClientResp resp = new RegisteredClientResp();
        resp.setId(registeredClient.getId());
        resp.setClientId(registeredClient.getClientId());
        resp.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt().atZone(ZoneId.systemDefault()).toLocalDateTime());
        resp.setClientName(registeredClient.getClientName());
        resp.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods());
        resp.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes());
        resp.setRedirectUris(registeredClient.getRedirectUris());
        resp.setPostLogoutRedirectUris(registeredClient.getPostLogoutRedirectUris());
        resp.setScopes(registeredClient.getScopes());

        return resp;
    }

}
