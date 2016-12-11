
package com.hf.hfw.application;

import com.hf.hfw.dao.lwmds.ConfigBuilder;
import com.hf.lwdatastore.DataStore;
import com.hf.lwdatastore.LWDataStoreFactory;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 *
 * @author pldorrell
 */
public class DBFactory {
    private static final Logger log = Logger.getLogger(DBFactoryShutdownListener.class);
    private boolean valid = false;
    private MongoTemplate template;
    protected LWDataStoreFactory lwDataStoreFactory;
    protected DataStore lwDataStore; 

    public DataStore getLwDataStore() {
        return lwDataStore;
    }
    
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public MongoTemplate getTemplate() {
        return template;
    }

    public void setTemplate(MongoTemplate template) {
        this.template = template;
    }
    
    public void initializeRepository(ApplicationRepositoryConfig config) {
        this.valid = false;
        if (config == null) {
            return;
        }
        this.initLWDataStore(config);
        return;
        /*
        if ("mongo".equals(config.getType())) {
            this.initMongo(config);
        } else if ("hypersql".equals(config.getType())) {
        } else if ("h2".equals(config.getType())) {
            
        } else if ("LWDataStore".equalsIgnoreCase(config.getType())){
            this.initLWDataStore(config);            
            //setup listener for shutdown
        }
        */
        
    }
    private void initLWDataStore(ApplicationRepositoryConfig config) {
            lwDataStoreFactory = new LWDataStoreFactory();
            ConfigBuilder configBuilder = new ConfigBuilder();
            configBuilder.setConfigurationDirectoryService(ApplicationState.getApplicationState().getConfigurationDirectoryService());
            lwDataStoreFactory.init(configBuilder.getConfig());
            lwDataStore = LWDataStoreFactory.getDataStore();
        
    }
    private void initMongo(ApplicationRepositoryConfig config){
            MongoFactoryBean mongoFactoryBean = new MongoFactoryBean();
            mongoFactoryBean.setHost(config.getHost());
            mongoFactoryBean.setPort(Integer.parseInt(config.getPort()));
            
            UserCredentials credentials = new UserCredentials(config.getUsername(), config.getPassword());
            
            try {
                this.template = new MongoTemplate(new Mongo(config.getHost(),Integer.parseInt(config.getPort())),config.getDb(),credentials);
                
                //template = new MongoTemplate(new Mongo())
                this.valid = true;
            } catch (UnknownHostException ex) {
                log.fatal("Unknown host when connecting to mongo");

            }
        
    }
    public void shutdown(){
        log.info("Shutting down");
    }
}
