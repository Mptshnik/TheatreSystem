package com.system.theatre.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class BaseController
{
    protected String getHtmlHeaderPath()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toString();

        String path = "";

        if(role.contains("ADMIN"))
        {
            path = "/headers/admin-header";
        }
        else if(role.contains("DIRECTOR"))
        {
            path = "/headers/director-header";
        }
        else if(role.contains("CASHIER"))
        {
            path = "/headers/cashier-header";
        }

        return path;
    }

    protected void insertHeader(Model model)
    {
        model.addAttribute("header", getHtmlHeaderPath());
    }
}
