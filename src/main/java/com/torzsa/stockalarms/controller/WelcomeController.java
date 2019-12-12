package com.torzsa.stockalarms.controller;


import com.torzsa.stockalarms.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
public class WelcomeController {

//    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showWelcomePage(ModelMap model) {
        model.put("username", Utils.getLoggedInUsername());
        return "welcome";
    }
}
