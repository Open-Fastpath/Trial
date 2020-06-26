package jp.flowershop.controller;

import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;
import jp.core.controller.InterfaceData;

public class AuthController extends WebController {

    @Override
    public String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {
        
        String error = input.getString("error");
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

        viewInterfaceData.set("error", error);
        viewInterfaceData.set("errorMessage", errorMessage);

        return "loginview";
    }
}

