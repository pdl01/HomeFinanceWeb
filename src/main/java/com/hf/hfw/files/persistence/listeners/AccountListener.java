package com.hf.hfw.files.persistence.listeners;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.hfw.files.DataFileLoader;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author pldor
 */
public class AccountListener implements ApplicationListener<AccountEvent>{

    private DataFileLoader dataFileLoader;

    public void setDataFileLoader(DataFileLoader dataFileLoader) {
        this.dataFileLoader = dataFileLoader;
    }
    
    @Override
    public void onApplicationEvent(AccountEvent e) {
        if (e.getType() == AccountEvent.AccountEventType.ADDED ||
            e.getType() == AccountEvent.AccountEventType.MODIFIED ||
            e.getType() == AccountEvent.AccountEventType.DELETED) {
            this.dataFileLoader.saveAccountsFile();    
        }
        
    }
    
}
