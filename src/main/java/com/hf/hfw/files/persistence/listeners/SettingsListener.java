/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.files.persistence.listeners;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.hfw.accounts.events.SettingsEvent;
import com.hf.hfw.files.DataFileLoader;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author pldor
 */
public class SettingsListener implements ApplicationListener<SettingsEvent>{
        private DataFileLoader dataFileLoader;

    public void setDataFileLoader(DataFileLoader dataFileLoader) {
        this.dataFileLoader = dataFileLoader;
    }
    
    @Override
    public void onApplicationEvent(SettingsEvent e) {
        if (e.getType() == SettingsEvent.SettingsEventType.MODIFIED) {
            this.dataFileLoader.saveSettingsFile();    
        }
        
    }

}
