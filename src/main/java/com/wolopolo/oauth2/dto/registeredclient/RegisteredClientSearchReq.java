package com.wolopolo.oauth2.dto.registeredclient;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.wolopolo.oauth2.dto.CommonSearchRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisteredClientSearchReq extends CommonSearchRequest {
    private String clientId;
    @JsonSetter(nulls = Nulls.SKIP)
    private String sortBy = "id";
    @JsonSetter(nulls = Nulls.SKIP)
    private String sortType = "DESC";
}
