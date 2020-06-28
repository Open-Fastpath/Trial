package jp.flowershop.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;   
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.flowershop.exception.InputException;
import jp.flowershop.exception.StatusException;
import jp.flowershop.exception.SystemException;

@Controller
@EnableAutoConfiguration
public class AuthController {

    @GetMapping("/start")
    public String service(@RequestParam String error, Model model)
    throws InputException, StatusException, SystemException {
       
        //修正　input.getString -> @RequestParam String error
        String errorMessage = "";

        if(error != null) {
            if (error.equals("noinput")) {
                errorMessage = "ユーザIDとパスワードを両方入力ください。";
            }
            else if (error.equals("invalid")) {
                errorMessage = "ユーザIDまたはパスワードが誤っています。";
            }
            else {
                errorMessage = "エラーが発生しました。管理者にご連絡ください。(SYSE-0999)";
            }
        }

        //修正 viewInterfaceData.set -> model.addAttribute
        model.addAttribute("error", error);
        model.addAttribute("errorMessage", errorMessage);

        //templateフォルダ配下の拡張子.htmlのファイルを読み込み
        return "loginview";
    }
}

