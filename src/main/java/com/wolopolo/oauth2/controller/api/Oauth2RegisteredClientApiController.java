package com.wolopolo.oauth2.controller.api;

import com.wolopolo.oauth2.dto.CommonResponse;
import com.wolopolo.oauth2.dto.OnCreate;
import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientReq;
import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientSearchReq;
import com.wolopolo.oauth2.service.Oauth2RegisteredClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class Oauth2RegisteredClientApiController {
    private final Oauth2RegisteredClientService registeredClientService;

    @PostMapping("/api/v1/admin/clients/search")
    public CommonResponse search(@RequestBody RegisteredClientSearchReq searchReq) {
        return CommonResponse.returnOk(registeredClientService.search(searchReq));
    }

    @PostMapping("/api/v1/admin/clients")
    public CommonResponse add(@RequestBody @Validated(OnCreate.class) RegisteredClientReq registeredClientReq) {
        return CommonResponse.returnOk(registeredClientService.save(registeredClientReq));
    }

    @GetMapping("/api/v1/admin/clients/{clientId}")
    public CommonResponse getDetail(@PathVariable("clientId") String clientId) {
        return CommonResponse.returnOk(registeredClientService.findByClientId(clientId));
    }

    @PutMapping("/api/v1/admin/clients")
    public CommonResponse update(@RequestBody @Valid RegisteredClientReq registeredClientReq) {
        registeredClientService.update(registeredClientReq);
        return CommonResponse.returnOk(null);
    }

    @PutMapping("/api/v1/admin/clients/secret")
    public CommonResponse changeSecret(@RequestBody RegisteredClientReq registeredClientReq) {
        registeredClientService.changeSecret(registeredClientReq);
        return CommonResponse.returnOk(null);
    }

    @DeleteMapping("/api/v1/admin/clients/{clientId}")
    public CommonResponse delete(@PathVariable("clientId") String clientId) {
        registeredClientService.delete(clientId);
        return CommonResponse.returnOk(null);
    }
}
