/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.web;

import com.hf.hfw.files.listeners.TransactionFileImportListener;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author pldorrell
 */
@Controller
public class MainController {
    private static final Logger log = Logger.getLogger(MainController.class);

    @RequestMapping(value = {"/", "/dashboard**"}, method = RequestMethod.GET)
    public ModelAndView dashBoard() {
        log.debug("Entering dashboard");
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is welcome page!");
        model.setViewName("dashboard");
                log.debug("Exiting dashboard");

        return model;
    }
    @RequestMapping(value = {"/budget"}, method = RequestMethod.GET)
    public ModelAndView budget() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is welcome page!");
        model.setViewName("budget");
        return model;
    }
    
    @RequestMapping(value = {"/settings"}, method = RequestMethod.GET)
    public ModelAndView settings() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is welcome page!");
        model.setViewName("settings");
        return model;
    }

}
