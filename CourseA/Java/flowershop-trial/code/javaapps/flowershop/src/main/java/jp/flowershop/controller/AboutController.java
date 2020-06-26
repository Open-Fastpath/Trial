package jp.flowershop.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;   
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableAutoConfiguration
public class AboutController {

    @GetMapping("/about")
    protected String service() {
        
        //templateフォルダの拡張子.htmlのファイルをテンプレートする
        return "aboutview";

    }
}
