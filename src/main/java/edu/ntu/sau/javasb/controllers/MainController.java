package edu.ntu.sau.javasb.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/")
    String welcome(Model model){
        return "welcome";
    }


}
