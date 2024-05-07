package com.wolopolo.oauth2.controller;

import com.wolopolo.oauth2.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class AccountController {
    private final AccountService accountService;

    @RequestMapping("/account")
    public String getHomePageForUser(Model model) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("accountInfo", accountService.getDetail(principal));
        return "/user/account";
    }

    @RequestMapping("/admin/accounts")
    public String getHomePageForAdmin() {
        return "/admin/accounts";
    }

}
