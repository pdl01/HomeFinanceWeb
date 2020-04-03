
package com.hf.hfw.application;

import com.hf.hfw.files.DataFileLoader;
import com.hf.hfw.license.Version;
import java.io.File;
//import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author phillip.dorrell
 */
public class ApplicationState implements InitializingBean, ApplicationContextAware {

    //private static final Logger log = Logger.getLogger(ApplicationState.class);

    private boolean configured = false;
    private boolean repositoryRunning = false;
    private Version currentVersion;
    private static ApplicationContext ctx;
    private ConfigurationDirectoryService configurationDirectoryService;
    private ApplicationRepositoryConfig applicationRepositoryConfig;
    private DBFactory dbFactory;
    private DataFileLoader dataFileLoader;

    public DataFileLoader getDataFileLoader() {
        return dataFileLoader;
    }

    public void setDataFileLoader(DataFileLoader dataFileLoader) {
        this.dataFileLoader = dataFileLoader;
    }
    
    public ApplicationRepositoryConfig getApplicationRepositoryConfig() {
        return applicationRepositoryConfig;
    }

    public ConfigurationDirectoryService getConfigurationDirectoryService() {
        return configurationDirectoryService;
    }

    public void setApplicationRepositoryConfig(ApplicationRepositoryConfig applicationRepositoryConfig) {
        this.applicationRepositoryConfig = applicationRepositoryConfig;
    }

    public boolean isRepositoryRunning() {
        return repositoryRunning;
    }

    public void setRepositoryRunning(boolean repositoryRunning) {
        this.repositoryRunning = repositoryRunning;
    }

    public DBFactory getDbFactory() {
        return dbFactory;
    }

    public void setDbFactory(DBFactory dbFactory) {
        this.dbFactory = dbFactory;
    }
    
    public boolean isConfigured() {
        return configured;
    }

    public void setConfigured(boolean configured) {
        this.configured = configured;
    }

    public Version getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Version currentVersion) {
        this.currentVersion = currentVersion;
    }

    public void init() {
        
        if (this.isConfigured()) {
            return;
        }
        
        //TODO load from the config path
        String application_home = System.getProperty("application.home");
        //if the application_home is not specified set up the default
        if (application_home == null) {
            //application_home = System.getProperty("user.home")+ File.separator +".hfw_app";
            application_home = this.configurationDirectoryService.getApplicationConfigurationDirectory();
            System.out.println("Using default application path"+application_home);
        }
        //System.setProperty("hfw_logging_path", this.configurationDirectoryService.getLoggingRepositoryDirectory());
        //LogManager.shutdown();
        //BasicConfigurator.resetConfiguration();
        
        //TODO initialize Logging
        //TODO initialize ApplicationRepo
        //application_home = "C:\\temp";
        //application_home = ("~/.hfw_dir");
        System.out.println("x:" + application_home);
        
        //if the directory doesn't exist create it and 
        //copy all the initialization stuff to it 
        File app_home_dir = new File(application_home);
        if (app_home_dir.exists() && app_home_dir.isDirectory()) {
            //assume that it is all setup and initialized
        } else if (app_home_dir.exists() && app_home_dir.isFile()){
            //TODO: this is bad log something or randomize an extension to set it up new
            
        } else {
            //create and initialize the dir
            app_home_dir.mkdirs();
            this.configurationDirectoryService.initializeApplicationConfiguration();
        }
        
        if (application_home != null) {
            
            
            File repo_config_file = new File(this.configurationDirectoryService.getRepoConfigFile());
            if (repo_config_file.exists()) {
                //log.debug("Loading Config:"+config_file.getPath());
                this.applicationRepositoryConfig = ApplicationRepositoryConfig.loadFromConfig(repo_config_file);
                if (this.applicationRepositoryConfig.isValid()) {
                    this.configured = true;
                }
            } else {
                //create a new one
                ApplicationRepositoryConfig.saveToConfig(new ApplicationRepositoryConfig(), this.configurationDirectoryService.getRepoConfigFile());
                //log.warn("Config not found:"+config_file.getPath());
                this.applicationRepositoryConfig = ApplicationRepositoryConfig.loadFromConfig(repo_config_file);
            }
        } else {
            //log.info("application.home variable is not set");/
            application_home = "~/.hfw_dir";
            File config_dir = new File(application_home);
            config_dir.mkdirs();
        }
        //initialize logging
        System.setProperty("hfw_logging_path", this.configurationDirectoryService.getLoggingRepositoryDirectory());
        
        System.out.println("Setting up log4j with config path:"+this.configurationDirectoryService.getLoggingConfigFile());
        
        //PropertyConfigurator.configure(this.configurationDirectoryService.getLoggingConfigFile());
		//log.info("Exiting init");
        // this.setupData();
        
        

        
        //DBFactory dbFactory = new DBFactory();
        if (this.applicationRepositoryConfig.isValid()) {
            dbFactory.initializeRepository(this.applicationRepositoryConfig);
            if (dbFactory.isValid()){
                repositoryRunning = true;
            }
        }

        this.dataFileLoader.performInitialLoad();
        
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

    public static ApplicationState getApplicationState() {
        return (ApplicationState) ctx.getBean("applicationState");
    }

    public void setConfigurationDirectoryService(ConfigurationDirectoryService configurationDirectoryService) {
        this.configurationDirectoryService = configurationDirectoryService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}
