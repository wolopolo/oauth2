package com.wolopolo.oauth2.service;

import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientReq;
import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientResp;
import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientSearchReq;
import org.springframework.data.domain.Page;

public interface Oauth2RegisteredClientService {
    RegisteredClientResp save(RegisteredClientReq registerClientReq);
    RegisteredClientResp findByClientId(String clientId);
    Page<RegisteredClientResp> search(RegisteredClientSearchReq registeredClientSearchReq);
    void update(RegisteredClientReq registerClientReq);
    void changeSecret(RegisteredClientReq registerClientReq);
    void delete(String clientId);
}
