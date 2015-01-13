
package com.hf.hfw.application;

import com.mongodb.Mongo;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 *
 * @author pldorrell
 */
public class DBFactory {
    private boolean valid = false;
    private MongoTemplate template;

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
        
        if ("mongo".equals(config.getType())) {
            MongoFactoryBean mongoFactoryBean = new MongoFactoryBean();
            mongoFactoryBean.setHost(config.getHost());
            mongoFactoryBean.setPort(Integer.parseInt(config.getPort()));
            
            UserCredentials credentials = new UserCredentials(config.getUsername(), config.getPassword());
            
            try {
                this.template = new MongoTemplate(new Mongo(config.getHost(),Integer.parseInt(config.getPort())),config.getDb(),credentials);
                
                //template = new MongoTemplate(new Mongo())
                this.valid = true;
            } catch (UnknownHostException ex) {
                Logger.getLogger(DBFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
}
