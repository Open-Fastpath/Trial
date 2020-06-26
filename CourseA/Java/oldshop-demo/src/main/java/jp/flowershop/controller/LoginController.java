package jp.flowershop.controller;

import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;
import jp.core.controller.InterfaceData;

public class LoginController extends WebController{

    private static final String APPROVED_USERID = "tencyo";
    private static final String APPROVED_PASSWORD = "ohanayasan";
    
    @Override
    public String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {
                
        String userId = input.getString("userId");
        String password = input.getString("password");

        if (userId == null || password == null || userId.equals("") || password.equals("")) {
            return "start?error=noinput";
        }

        if (!userId.equals(APPROVED_USERID) || 
            !password.equals(APPROVED_PASSWORD)) {
            return "start?error=invalid";
        }

        return "menu";
    }

}
