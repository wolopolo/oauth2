package com.wolopolo.oauth2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegisterReq {
    @Email
    @NotBlank
    @Length(max = 30)
    private String email;

    @NotBlank
    @Length(max = 30)
    private String name;

    @NotBlank
    @Length(min = 8, max = 30)
    private String password;
}
