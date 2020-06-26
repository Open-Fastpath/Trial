package jp.flowershop.controller;

import jp.core.controller.WebController;
import jp.core.exception.InputException;
import jp.core.exception.StatusException;
import jp.core.exception.SystemException;
import jp.core.controller.InterfaceData;

public class AboutController extends WebController{

    @Override
    protected String service(InterfaceData input, InterfaceData viewModel, InterfaceData state)
    throws InputException, StatusException, SystemException {
        
        return "aboutview";

    }
}
