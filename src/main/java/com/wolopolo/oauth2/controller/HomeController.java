package com.wolopolo.oauth2.controller;

import com.wolopolo.oauth2.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final AccountService accountService;

    @RequestMapping("/")
    public RedirectView getHomePage() {
        Set<String> authorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if(authorities.contains("ROLE_ADMIN")) {
            return new RedirectView("/admin/accounts");
        }

        return new RedirectView("/account");
    }

    @RequestMapping(value = {"/index", "/index.html"})
    public String getIndex() {
        return "/index";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "/login";
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "/register";
    }

    @RequestMapping("/verify/{token}")
    public String verifyAccount(@PathVariable("token") String token) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        if(accountService.verify(principal, token)) {
            return "redirect:/account";
        } else {
            return "/verificationfailed";
        }
    }

    @GetMapping("/change-password")
    public String getChangePasswordForm(Model model) {
        model.addAttribute("principal", SecurityContextHolder.getContext().getAuthentication().getName());
        return "/changepassword";
    }

    @GetMapping("/request-forgot-password")
    public String getRequestForgotPasswordForm() {
        return "/requestforgotpassword";
    }

    @GetMapping("/forgot-password/{token}")
    public String getForgotPasswordForm(Model model, @PathVariable("token") String token) {
        model.addAttribute("token", token);
        return "/forgotpassword";
    }
}
