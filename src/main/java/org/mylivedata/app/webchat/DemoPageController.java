package org.mylivedata.app.webchat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DemoPageController {
    
    @RequestMapping(value="/demo", method=RequestMethod.GET)
    public String resetPassword (Model m) throws Exception {
        return "demo";
    }
    
}
