package com.hf.hfw.dao.lwmds;

import com.hf.hfw.application.ConfigurationDirectoryService;
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
import java.io.File;

/**
 *
 * @author pldorrell
 */
public class ConfigBuilder {
    public final static String COLLECTION_ACCOUNTS = "account";
    public final static String COLLECTION_TRANSACTIONS = "transactions";
    public final static String COLLECTION_ONLINE_TRANSACTIONS = "onlineTransactions";
    public final static String COLLECTION_SCHEDULED_TRANSACTIONS = "scheduledTransactions";
    public final static String COLLECTION_BUDGET = "budgets";
    
    private ConfigurationDirectoryService configurationDirectoryService;
    public void setConfigurationDirectoryService(ConfigurationDirectoryService configurationDirectoryService){
        this.configurationDirectoryService = configurationDirectoryService;
    }
    public DataStoreConfig getConfig() {
        DataStoreConfigImpl dsConfig = new DataStoreConfigImpl();

        
        dsConfig.setDataDir(this.configurationDirectoryService.getDataStorageDirectory() + File.separator+"lwds" );

        
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
        collectionDescriptionImpl.setName(COLLECTION_ACCOUNTS);
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
        collectionDescriptionImpl.setName(COLLECTION_BUDGET);
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.setConverter(new BudgetConverter());
        return collectionDescriptionImpl;
    }

    private CollectionDescription buildTransactionCollectionDescription() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName(COLLECTION_TRANSACTIONS);
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.addIndexedAttribute("accountId");
        collectionDescriptionImpl.addIndexedAttribute("category");
        collectionDescriptionImpl.addIndexedAttribute("month");
        collectionDescriptionImpl.addIndexedAttribute("year");
        collectionDescriptionImpl.addIndexedAttribute("statusTxt");
        collectionDescriptionImpl.setConverter(new TransactionConverter());
        return collectionDescriptionImpl;
    }

    private CollectionDescription buildOnlineTransactionCD() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName(COLLECTION_ONLINE_TRANSACTIONS);
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.addIndexedAttribute("accountId");
        collectionDescriptionImpl.setConverter(new OnlineTransactionConverter());
        return collectionDescriptionImpl;
    }
    private CollectionDescription buildScheduledTransactionCD() {
        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName(COLLECTION_SCHEDULED_TRANSACTIONS);
        collectionDescriptionImpl.setIdAttribute("id");        
        collectionDescriptionImpl.addIndexedAttribute("accountId");
        collectionDescriptionImpl.addIndexedAttribute("month");
        collectionDescriptionImpl.addIndexedAttribute("year");
        collectionDescriptionImpl.addIndexedAttribute("original");
        collectionDescriptionImpl.addIndexedAttribute("originalTransactionId");
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
