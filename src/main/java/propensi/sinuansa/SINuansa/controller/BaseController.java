package propensi.sinuansa.SINuansa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
