package com.wolopolo.oauth2.mapper;

import com.wolopolo.oauth2.dto.account.AccountResp;
import com.wolopolo.oauth2.dto.account.AccountRegisterReq;
import com.wolopolo.oauth2.entity.Account;
import org.springframework.beans.BeanUtils;

public class AccountMapper {
    public static Account convert(AccountRegisterReq accountRegisterReq) {
        Account account = new Account();
        BeanUtils.copyProperties(accountRegisterReq, account);

        return account;
    }

    public static AccountResp convert(Account account) {
        AccountResp accountResp = new AccountResp();
        BeanUtils.copyProperties(account, accountResp);

        return accountResp;
    }
}
