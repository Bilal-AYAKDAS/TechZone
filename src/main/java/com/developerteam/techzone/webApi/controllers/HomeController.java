package com.developerteam.techzone.webApi.controllers;

import com.developerteam.techzone.business.concreates.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {


    @GetMapping("/")
    public String root() {
        return "forward:/html/public/index.html";
    }

    @GetMapping("/public/{page}")
    public String publicPage(@PathVariable String page) {
        return "forward:/html/public/" + page + ".html";
    }

    @GetMapping("/admin/{page}")
    public String adminPage(@PathVariable String page, @RequestParam(required = false) String id) {
        // Eğer bir id parametresi varsa, sayfaya bunu ekleyerek yönlendirin
        if (id != null) {
            return "forward:/html/admin/" + page + ".html?id=" + id;
        }
        // Eğer id yoksa standart sayfa yönlendirmesi yapın
        return "forward:/html/admin/" + page + ".html";
    }

    @GetMapping("/customer/{page}")
    public String customerPage(@PathVariable String page) {
        return "forward:/html/customer/" + page + ".html";
    }


}
