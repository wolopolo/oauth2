package com.wolopolo.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Oauth2RegisteredClientController {
    @RequestMapping("/admin/clients")
    public String getHomePageForAdmin() {
        return "/admin/clients";
    }
}
