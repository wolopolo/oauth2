package com.wolopolo.oauth2.service;

import com.wolopolo.oauth2.dto.account.AccountRegisterReq;
import com.wolopolo.oauth2.dto.account.AccountResp;
import com.wolopolo.oauth2.dto.account.AccountSearchReq;
import com.wolopolo.oauth2.dto.account.ChangePasswordReq;
import com.wolopolo.oauth2.dto.account.ForgotPasswordReq;
import org.springframework.data.domain.Page;

public interface AccountService {
    AccountResp save(AccountRegisterReq accountRegisterReq);
    AccountResp getDetail(String email);
    Page<AccountResp> search(AccountSearchReq searchRequest);
    AccountResp update(AccountSearchReq searchRequest);
    boolean verify(String principal, String token);
    void requestForgotPassword(String email);
    void forgotPassword(ForgotPasswordReq forgotPasswordReq);
    void changePassword(String principal, ChangePasswordReq changePasswordReq);
    boolean lock();
}
