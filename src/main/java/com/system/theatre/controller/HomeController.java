package com.system.theatre.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
    @GetMapping("/")
    public String index()
    {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String success(Authentication authentication)
    {
        String role = authentication.getAuthorities().toString();
        if(role.contains("ADMIN"))
        {
            return "/admin/index";
        }
        else if(role.contains("DIRECTOR"))
        {
            return "/director/index";
        }
        else if(role.contains("CASHIER"))
        {
            return "/cashier/index";
        }

        return "/home/index";
    }
}
