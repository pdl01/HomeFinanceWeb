package com.hf.hfw.dao.mongo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class AbstractMongoDAO {
	protected MongoTemplate mongoTemplate;
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	protected MongoOperations getMongoOperation () {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		return mongoOperation;
	}
	
	
	
}