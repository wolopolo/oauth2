package com.wolopolo.oauth2.dto.account;

import com.wolopolo.oauth2.dto.CommonSearchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSearchReq extends CommonSearchRequest {
    private String email;
    private String name;
    private String role;
    private Boolean isVerified;
    private Boolean isActive;
}
