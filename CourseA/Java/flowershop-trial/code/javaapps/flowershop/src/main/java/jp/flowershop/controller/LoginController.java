package jp.flowershop.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;   
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@EnableAutoConfiguration
public class LoginController {

    private final String APPROVED_USERID = "tencyo";
    private final String APPROVED_PASSWORD = "ohanayasan";
    
    @PostMapping("/login")
    public String service(@RequestParam String userId, @RequestParam String password){
                
        if (userId == null || password == null || 
            userId.equals("") || password.equals("")) {
            return "redirect:start?error=noinput";
        }

        if (!userId.equals(APPROVED_USERID) || 
            !password.equals(APPROVED_PASSWORD)) {
            return "redirect:start?error=invalid";
        }

        //認証で問題なければメニューにリダイレクト
        //POST処理のあとはコマンドの２重処理を避けるため
        //必ずGETにリダイレクトするーCQRS
        return "redirect:menu";
    }
}
