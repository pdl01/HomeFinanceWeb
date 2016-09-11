package com.hf.hfw.dao.mongo;

import com.hf.hfw.application.DBFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class AbstractMongoDAO {
	private MongoTemplate mongoTemplate;
	protected DBFactory dbFactory;

    public DBFactory getDbFactory() {
        return dbFactory;
    }

    public void setDbFactory(DBFactory dbFactory) {
        this.dbFactory = dbFactory;
    }
        public MongoTemplate getMongoTemplate()  {
            if (dbFactory.isValid()) {
                return dbFactory.getTemplate();
            } else {
                System.out.println("Repository not initialized");
                return null;
            } 
            
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
        /*
	protected MongoOperations getMongoOperation () {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		return mongoOperation;
	}
        */
	
	
	
}