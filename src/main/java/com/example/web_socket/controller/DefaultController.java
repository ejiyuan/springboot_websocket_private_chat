package com.example.web_socket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class DefaultController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
