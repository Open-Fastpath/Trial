package jp.flowershop.controller;

import java.time.LocalDateTime;

import jp.core.controller.InterfaceData;
import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;
import jp.core.util.DateTimeUtil;

public class MenuController extends WebController{

    protected String service(InterfaceData input, InterfaceData viewInterfaceData, InterfaceData state)
    throws InputException, StatusException, SystemException {
        
        viewInterfaceData.set("userName", "店長");
        viewInterfaceData.set("currDate", DateTimeUtil.toViewJpString(LocalDateTime.now()));

        return "menuview";
    }

}
