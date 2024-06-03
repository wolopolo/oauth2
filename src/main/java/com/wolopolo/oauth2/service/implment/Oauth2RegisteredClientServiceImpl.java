package com.wolopolo.oauth2.service.implment;

import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientReq;
import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientResp;
import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientSearchReq;
import com.wolopolo.oauth2.mapper.RegisteredClientMapper;
import com.wolopolo.oauth2.repository.Oauth2RegisteredClientRepo;
import com.wolopolo.oauth2.repository.specification.Oauth2RegisteredClientSpecification;
import com.wolopolo.oauth2.service.Oauth2RegisteredClientService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class Oauth2RegisteredClientServiceImpl implements Oauth2RegisteredClientService {
    private final PasswordEncoder passwordEncoder;
    private final RegisteredClientRepository registeredClientRepository;
    private final Oauth2RegisteredClientRepo oauth2RegisteredClientRepo;

    @Override
    public RegisteredClientResp save(RegisteredClientReq registerClientReq) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(registerClientReq.getClientId())
                .clientSecret(passwordEncoder.encode(registerClientReq.getClientSecret()))
                .clientIdIssuedAt(Instant.now())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(registerClientReq.getRedirectUris())
                .scope(registerClientReq.getScopes())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofSeconds(registerClientReq.getTokenTimeToLive()))
                        .reuseRefreshTokens(true)
                        .build())
                .build();

        registeredClientRepository.save(registeredClient);

        return RegisteredClientMapper.convert(registeredClient);
    }

    @Override
    public RegisteredClientResp findByClientId(String clientId) {
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
        return registeredClient != null ? RegisteredClientMapper.convert(registeredClient) : null;
    }

    @Override
    public Page<RegisteredClientResp> search(RegisteredClientSearchReq searchReq) {
        Pageable pageable = PageRequest.of(searchReq.getPage(), searchReq.getSize(),
                Sort.by(Sort.Direction.valueOf(searchReq.getSortType()), searchReq.getSortBy()));

        return oauth2RegisteredClientRepo.findAll(Oauth2RegisteredClientSpecification.search(searchReq), pageable.previousOrFirst())
                .map(RegisteredClientMapper::convert);
    }

    @Transactional
    @Override
    public void update(RegisteredClientReq registerClientReq) {
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(registerClientReq.getClientId());
        if(registeredClient != null) {
            TokenSettings tokenSettings = registeredClient.getTokenSettings();

            RegisteredClient.Builder savedRegisteredClientBuilder = RegisteredClient.from(registeredClient)
                    .redirectUris(redirectUris -> {
                        redirectUris.clear();
                        redirectUris.add(registerClientReq.getRedirectUris());
                    })
                    .scopes(scopes -> {
                        scopes.clear();
                        scopes.add(registerClientReq.getScopes());
                    })
                    .tokenSettings(TokenSettings.withSettings(tokenSettings.getSettings())
                            .accessTokenTimeToLive(Duration.ofSeconds(registerClientReq.getTokenTimeToLive()))
                            .build());

            if(!StringUtils.isEmpty(registerClientReq.getClientSecret())
                    && !StringUtils.isBlank(registerClientReq.getClientSecret())) {
                savedRegisteredClientBuilder.clientSecret(passwordEncoder.encode(registerClientReq.getClientSecret()));
            }

            registeredClientRepository.save(savedRegisteredClientBuilder.build());
        }
    }

    @Transactional
    @Override
    public void changeSecret(RegisteredClientReq registerClientReq) {
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(registerClientReq.getClientId());
        if(registeredClient != null) {
            RegisteredClient savedRegisteredClient = RegisteredClient.from(registeredClient)
                    .clientSecret(passwordEncoder.encode(registerClientReq.getClientSecret()))
                    .build();

            registeredClientRepository.save(savedRegisteredClient);
        }
    }

    @Override
    public void delete(String clientId) {
        oauth2RegisteredClientRepo.delete(Oauth2RegisteredClientSpecification.equalClientId(clientId));
    }
}
