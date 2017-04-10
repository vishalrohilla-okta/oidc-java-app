package com.okta.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.okta.sample.model.RegisterModel;
import com.okta.sample.model.RegisterResult;
import com.okta.sample.service.RegisterService;

import java.security.Principal;

@Controller
public class AppController {

    @Value("#{ @environment['okta.redirect.uri'] }")
    protected String oktaRedirectUri;

    @Value("#{ @environment['okta.api.url'] }")
    protected String oktaApiUrl;

    @Value("#{ @environment['security.oauth2.client.clientId'] }")
    protected String oktaApiClientId;

    @Autowired
    RegisterService registerService;

    @RequestMapping("/")
    public String hello() {
        return "redirect:/register";
    }

    @RequestMapping("/doit")
    public String login(Model model, Principal user) {
        if (user != null) { return "redirect:/secure/secure/pdf"; }

        model.addAttribute("oktaApiUrl", oktaApiUrl);
        model.addAttribute("oktaApiClientId", oktaApiClientId);
        model.addAttribute("oktaRedirectUri", oktaRedirectUri);

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String viewRegister() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegister(RegisterModel registerForm, Model model) {
        RegisterResult result = registerService.register(registerForm);

        model.addAttribute("result", result);

        return "register_result";
    }
}
