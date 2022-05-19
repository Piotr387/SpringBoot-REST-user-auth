package com.pp.app.ui.model.request.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping(path = "/email-verification-page")
    public String verifyEmailTokenPage(Model model) {
        return "email-verification";
    }

    @RequestMapping(path = "/password-reset")
    public String passwordReset(Model model) {
        return "password-reset";
    }
}
