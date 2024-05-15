package com.wolopolo.oauth2.controller.api;

import com.wolopolo.oauth2.dto.CommonResponse;
import com.wolopolo.oauth2.dto.account.AccountRegisterReq;
import com.wolopolo.oauth2.dto.account.AccountSearchReq;
import com.wolopolo.oauth2.dto.account.ChangeAccountStatusReq;
import com.wolopolo.oauth2.dto.account.ChangePasswordReq;
import com.wolopolo.oauth2.dto.account.ForgotPasswordReq;
import com.wolopolo.oauth2.dto.account.RequestForgotPasswordReq;
import com.wolopolo.oauth2.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountApiController {
    private final AccountService accountService;

    @PostMapping("/api/v1/register")
    public CommonResponse register(@RequestBody @Valid AccountRegisterReq accountRegisterReq) {
        return CommonResponse.returnOk(accountService.save(accountRegisterReq));
    }

    @PostMapping("/api/v1/admin/accounts")
    public CommonResponse search(@RequestBody AccountSearchReq accountSearchReq) {
        return CommonResponse.returnOk(accountService.search(accountSearchReq));
    }

    @PostMapping("/api/v1/admin/change-active-status")
    public CommonResponse changeActiveStatus(@RequestBody @Valid ChangeAccountStatusReq changeAccountStatusReq) {
        accountService.changeActiveStatus(changeAccountStatusReq.getEmail());
        return CommonResponse.returnOk(null);
    }

    @PostMapping("/api/v1/change-password")
    public CommonResponse changePassword(@RequestBody @Valid ChangePasswordReq changePasswordReq) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.changePassword(principal, changePasswordReq);
        return CommonResponse.returnOk(null);
    }

    @PostMapping("/api/v1/request-forgot-password")
    public CommonResponse requestForgotPassword(@RequestBody @Valid RequestForgotPasswordReq forgotPasswordReq) {
        accountService.requestForgotPassword(forgotPasswordReq.getEmail());
        return CommonResponse.returnOk(null);
    }

    @PostMapping("/api/v1/forgot-password")
    public CommonResponse changePassword(@RequestBody @Valid ForgotPasswordReq forgotPasswordReq) {
        accountService.forgotPassword(forgotPasswordReq);
        return CommonResponse.returnOk(null);
    }
}
