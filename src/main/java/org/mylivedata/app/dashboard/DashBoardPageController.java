package org.mylivedata.app.dashboard;

import javax.servlet.http.HttpSession;

import org.mylivedata.app.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lubo08 on 14.3.2014.
 */
@Controller
public class DashBoardPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(DashBoardPageController.class);

    @Autowired
    private SessionUtils sessionUtils;

    @RequestMapping(value={"/","/dashboard"}, method= RequestMethod.GET)
    public String loadDashboardPage(Model m, HttpSession session) {
        sessionUtils.setDashboardInfo(m,session);
        return "dashboard";
    }

    @RequestMapping(value="/visitor-list", method= RequestMethod.GET)
    public String loadVisitorsListPage(Model m, HttpSession session) {
        sessionUtils.setDashboardInfo(m,session);
        return "visitor_list";
    }

    @RequestMapping(value="/layout", method= RequestMethod.GET)
    public String loadConfigurationPage(Model m, HttpSession session) {
        sessionUtils.setDashboardInfo(m,session);
        return "layout";
    }

    @RequestMapping(value="/chat/template/{type}", method= RequestMethod.GET)
    public String loadBubbleHtml(Model m, HttpSession session, @PathVariable String type) {

        return "db:"+type+":bubble_style";
    }

    @RequestMapping(value="/timeline", method= RequestMethod.GET)
    public String loadTimeline(Model m) {
        return "timeline";
    }
    
    @RequestMapping(value="/gallery", method= RequestMethod.GET)
    public String loadGallery(Model m) {
        return "gallery";
    }

}
