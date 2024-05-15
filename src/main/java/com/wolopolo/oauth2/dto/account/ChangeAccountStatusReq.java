package com.wolopolo.oauth2.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeAccountStatusReq {
    @Email
    @NotBlank
    private String email;
}
