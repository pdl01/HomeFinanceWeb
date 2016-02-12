/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.lwmds;

import com.hf.hfw.dao.lwmds.converter.AccountConverter;
import com.hf.hfw.dao.lwmds.converter.BudgetConverter;
import com.hf.hfw.dao.lwmds.converter.DailyBalanceConverter;
import com.hf.hfw.dao.lwmds.converter.NotificationConverter;
import com.hf.hfw.dao.lwmds.converter.OnlineTransactionConverter;
import com.hf.hfw.dao.lwmds.converter.ScheduledTransactionConverter;
import com.hf.hfw.dao.lwmds.converter.SettingsConverter;
import com.hf.hfw.dao.lwmds.converter.TransactionConverter;
import com.hf.lwdatastore.CollectionDescription;
import com.hf.lwdatastore.CollectionDescriptionImpl;
import com.hf.lwdatastore.DataStoreConfig;
import com.hf.lwdatastore.DataStoreConfigImpl;

/**
 *
 * @author pldorrell
 */
public class ConfigBuilder {

    public DataStoreConfig getConfig() {
        DataStoreConfigImpl dsConfig = new DataStoreConfigImpl();

        dsConfig.setDataDir("/home/pldorrell/.hfw_app/data");

        
        dsConfig.addCollection(buildAccountCollectionDescription());
        dsConfig.addCollection(buildBudgetCollectionDescription());
        dsConfig.addCollection(buildTransactionCollectionDescription());
        dsConfig.addCollection(buildOnlineTransactionCD());
        dsConfig.addCollection(buildScheduledTransactionCD());
        dsConfig.addCollection(buildDailyBalanceCD());
        dsConfig.addCollection(buildSettingsCD());
        dsConfig.addCollection(buildNotificationCD());
        return dsConfig;
    }
    
    private CollectionDescription buildAccountCollectionDescription() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName("account");
        collectionDescriptionImpl.setIdAttribute("id");
        collectionDescriptionImpl.addIndexedAttribute("accountNumber");
        collectionDescriptionImpl.addIndexedAttribute("name");
        //collectionDescriptionImpl.addIndexedAttribute("externalId");
        
        collectionDescriptionImpl.addIndexedAttribute("accountType");
        
        collectionDescriptionImpl.setConverter(new AccountConverter());
        return collectionDescriptionImpl;
    }
    
    private CollectionDescription buildBudgetCollectionDescription() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName("budget");
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.setConverter(new BudgetConverter());
        return collectionDescriptionImpl;
    }

    private CollectionDescription buildTransactionCollectionDescription() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName("transactions");
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.addIndexedAttribute("accountId");
        collectionDescriptionImpl.addIndexedAttribute("category");
        collectionDescriptionImpl.addIndexedAttribute("month");
        collectionDescriptionImpl.addIndexedAttribute("year");
        collectionDescriptionImpl.setConverter(new TransactionConverter());
        return collectionDescriptionImpl;
    }

    private CollectionDescription buildOnlineTransactionCD() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName("onlineTransactions");
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.addIndexedAttribute("accountId");
        collectionDescriptionImpl.setConverter(new OnlineTransactionConverter());
        return collectionDescriptionImpl;
    }
    private CollectionDescription buildScheduledTransactionCD() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName("scheduledTransactions");
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.addIndexedAttribute("accountId");
        collectionDescriptionImpl.setConverter(new ScheduledTransactionConverter());
        return collectionDescriptionImpl;
    }    
    private CollectionDescription buildDailyBalanceCD() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName("dailyBalance");
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.addIndexedAttribute("accountId");
        collectionDescriptionImpl.setConverter(new DailyBalanceConverter());
        return collectionDescriptionImpl;
    } 
    private CollectionDescription buildSettingsCD() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName("settings");
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.addIndexedAttribute("typeOfSetting");
        collectionDescriptionImpl.setConverter(new SettingsConverter());
        return collectionDescriptionImpl;
    }     
    private CollectionDescription buildNotificationCD() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName("notifications");
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.addIndexedAttribute("status");
        collectionDescriptionImpl.setConverter(new NotificationConverter());
        return collectionDescriptionImpl;
    }

}
