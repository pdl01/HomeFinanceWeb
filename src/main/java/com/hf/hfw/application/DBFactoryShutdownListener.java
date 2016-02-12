/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.application;

import com.hf.hfw.files.listeners.TransactionFileImportListener;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 *
 * @author pldorrell
 */
public class DBFactoryShutdownListener implements ApplicationListener<ApplicationEvent>{
private static final Logger log = Logger.getLogger(DBFactoryShutdownListener.class);
    @Override
    public void onApplicationEvent(ApplicationEvent e) {
        log.info("System Shutting down:");
        ApplicationState.getApplicationState().getDbFactory().shutdown();
    }
    
}
