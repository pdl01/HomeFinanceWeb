
package com.hf.hfw.web;

import com.hf.hfw.files.listeners.TransactionFileImportListener;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author pldorrell
 */
@Controller
public class MainController {
    private static final Logger log = LogManager.getLogger(MainController.class);

    @RequestMapping(value = {"/", "/dashboard**"}, method = RequestMethod.GET)
    public String dashBoard(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        log.debug("Entering dashboard");
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.addAttribute("theme",request.getAttribute("theme"));
        //model.addAttribute("theme",request.getAttribute("theme"));
        
        //model.setViewName("dashboard");
        log.debug("Exiting dashboard");
        return "dashboard";
    }
    
    @RequestMapping(value = {"/accounts**"}, method = RequestMethod.GET)
    public String accounts(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        log.debug("Entering dashboard");
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.setViewName("dashboard");
        log.debug("Exiting dashboard");
        return "accounts";
    }
    
    @RequestMapping(value = {"/budget"}, method = RequestMethod.GET)
    public String budget(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.setViewName("budget");
        return "budget";
    }
    
    @RequestMapping(value = {"/settings"}, method = RequestMethod.GET)
    public String settings(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.setViewName("settings");
        return "settings";
    }

    @RequestMapping(value = {"/notifications"}, method = RequestMethod.GET)
    public String notifications(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.setViewName("settings");
        return "notifications";
    }

    @RequestMapping(value = {"/reports"}, method = RequestMethod.GET)
    public String reports(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.setViewName("settings");
        return "reports";
    }

    @RequestMapping(value = {"/transaction"}, method = RequestMethod.GET)
    public String transactionDisplay(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.setViewName("settings");
        return "transaction";
    }

    @RequestMapping(value = {"/version"}, method = RequestMethod.GET)
    public String version(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.setViewName("settings");
        return "version";
    }
    
    @RequestMapping(value = {"/mobile","/mobile/accounts"}, method = RequestMethod.GET)
    public ModelAndView mobileAccount() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is welcome page!");
        model.setViewName("mobile/accounts");
        return model;
    }

    @RequestMapping(value = {"/mobile/registry/*"}, method = RequestMethod.GET)
    public ModelAndView mobileRegistry(@RequestParam("account") String account) {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is welcome page!");
        model.addObject("account",account);
        model.setViewName("mobile/registry");
        return model;
    }

    @RequestMapping(value = {"/mobile/transaction/new/{account}"}, method = RequestMethod.GET)
    public ModelAndView mobileTransaction(@PathVariable String account) {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is welcome page!");
        model.addObject("account",account);
        model.setViewName("mobile/transaction");
        return model;
    }

    @RequestMapping(value = {"/uploadresults"}, method = RequestMethod.POST)
    public String uploadResults(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        log.debug("Entering uploadResults");
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.setViewName("dashboard");
        log.debug("Exiting uploadResults");
        return "uploadResults";
    }
    
    @RequestMapping(value = {"/fileops**"}, method = RequestMethod.GET)
    public String fileOps(@ModelAttribute("model") ModelMap model,HttpServletRequest request) {
        log.debug("Entering fileOps");
        //ModelAndView model = new ModelAndView();
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");
        model.addAttribute("theme",request.getAttribute("theme"));
        //model.setViewName("dashboard");
        log.debug("Exiting fileOps");
        return "fileops";
    }
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/app/dashboard?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginPage (HttpServletRequest request, HttpServletResponse response) {
        
        Enumeration<String> attributes = request.getAttributeNames();
        
        return "login";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
    
}
