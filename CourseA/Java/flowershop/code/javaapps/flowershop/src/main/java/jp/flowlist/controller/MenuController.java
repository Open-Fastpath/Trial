package jp.flowlist.controller;

import java.text.SimpleDateFormat;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableAutoConfiguration
public class MenuController   {
    
    @GetMapping("/")
    public String menu(Model model){

        model.addAttribute("userName", "○○さん");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日（EEE）HH時mm分");
        model.addAttribute("currDate",  formatter.format(System.currentTimeMillis()));

        return("index");
    }

}