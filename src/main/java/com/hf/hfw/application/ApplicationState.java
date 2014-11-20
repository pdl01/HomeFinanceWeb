/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.application;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author phillip.dorrell
 */
public class ApplicationState implements InitializingBean,ApplicationContextAware{
	//private static final Logger log = Logger.getLogger(ApplicationState.class);
	private boolean configured = false;
	private static ApplicationContext ctx;
	

   
        
	public boolean isConfigured() {
		return configured;
	}
	public void setConfigured(boolean configured) {
		this.configured = configured;
	}
	
	
	public void init() {
            if (this.isConfigured()) {
                return;
            }
                //log.info("Entering init");
		//TODO load from the config path
		String application_home = System.getProperty("application.home");
                //System.setProperty("keystone_logging_path", this.configurationDirectoryService.getLoggingRepositoryDirectory());
                //LogManager.shutdown();
                //BasicConfigurator.resetConfiguration();
                
                //TODO initialize Logging
                //TODO initialize ApplicationRepo
                //application_home = "C:\\temp";
		//if (application_home != null) {
			//File config_file = new File(application_home + File.separator + "application_home.json");
		//	if (config_file.exists()) {
				//log.debug("Loading Config:"+config_file.getPath());
				//this.applicationRepositoryConfig = ApplicationRepositoryConfig.loadFromConfig(config_file);
				//if (this.applicationRepositoryConfig.isValid()) {
				//	this.configured = true;
				//}
			//}else {
				//log.warn("Config not found:"+config_file.getPath());
		//	}
		//} else {
                    //log.info("application.home variable is not set");
                    //File config_dir = new File(application_home);
                    //config_dir.mkdirs();
                //}
		//log.info("Exiting init");
               // this.setupData();
	}
        /*
        protected void setupData(){
            SetupAdminUser setupAdminUserJob = new SetupAdminUser();
            setupAdminUserJob.setUserManager((UserManager)getCtx().getBean("userManager"));
            if (setupAdminUserJob.shouldRun()) {
                setupAdminUserJob.execute();
            }
        }
        */
        
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {	
		this.ctx = applicationContext;
	}
	public ApplicationContext getCtx() {
		return ctx;
	}
	public void setCtx(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	public static ApplicationState getApplicationState(){
		return (ApplicationState) ctx.getBean("applicationState");
	}
/*
    public void setConfigurationDirectoryService(ConfigurationDirectoryService configurationDirectoryService) {
        this.configurationDirectoryService = configurationDirectoryService;
    }
*/
    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}