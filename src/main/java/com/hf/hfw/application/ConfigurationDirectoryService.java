package com.hf.hfw.application;

import java.io.File;
import java.io.IOException;
//import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author phillip
 */
public class ConfigurationDirectoryService {

    public void init() {

    }

    public void initializeApplicationConfiguration() {
        this.initializeConfigurationDirectory();
        this.initializeImageStorageDirectory();
        this.initializeLoggingConfigurationDirectory();
        this.initializeLoggingRepositoryDirectory();
        this.initializeFileStorageDirectory();
        this.initializeTempFileStorageDirectory();
        this.initializeDataStorageDirectory();
        try {
            System.out.println("file:" + ApplicationState.getApplicationState().getCtx().getResource("config/log4j.properties").getFile().getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Setting up log4j with config path:" + this.getLoggingConfigFile());
       // PropertyConfigurator.configure(this.getLoggingConfigFile());
        System.out.println("Finished Setting up log4j");

    }

    public void initializeConfigurationDirectory() {
        File file = new File(this.getApplicationConfigurationDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            System.out.println(ApplicationState.getApplicationState().getCtx().getResource("WEB-INF/config/application_repository.properties").getFile().getPath());
            this.copyIfNotExists(ApplicationState.getApplicationState().getCtx().getResource("WEB-INF/config/application_repository.properties").getFile().getPath(), this.getApplicationConfigurationDirectory() + File.separator + "application_repository.properties");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            this.copyIfNotExists(ApplicationState.getApplicationState().getCtx().getResource("WEB-INF/config/emailservices.properties").getFile().getPath(), this.getApplicationConfigurationDirectory() + File.separator + "emailservices.properties");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void copyIfNotExists(String war_file_path, String application_config_path) {
        File source = new File(war_file_path);
        File dest = new File(application_config_path);
        if (!dest.exists()) {
            try {
                FileCopyUtils.copy(source, dest);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Using config file:" + application_config_path);
    }

    public void initializeLoggingConfigurationDirectory() {
        File file = new File(this.getLoggingConfigurationDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }
        try {

            this.copyIfNotExists(ApplicationState.getApplicationState().getCtx().getResource("WEB-INF/config/log4j.properties").getFile().getPath(), this.getLoggingConfigFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void initializeLoggingRepositoryDirectory() {
        File file = new File(this.getLoggingRepositoryDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void initializeImageStorageDirectory() {
        File file = new File(this.getImageStorageDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void initializeFileStorageDirectory() {
        File file = new File(this.getFileStorageDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void initializeTempFileStorageDirectory() {
        File file = new File(this.getTempFileStorageDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public String getImageStorageDirectory() {
        return this.getApplicationConfigurationDirectory() + File.separator + "images";
    }

    public String getFileStorageDirectory() {
        return this.getApplicationConfigurationDirectory() + File.separator + "files";
    }

    public String getTempFileStorageDirectory() {
        return this.getApplicationConfigurationDirectory() + File.separator + "temp";
    }

    public String getLoggingRepositoryDirectory() {
        return this.getApplicationConfigurationDirectory() + File.separator + "logs";
    }

    public String getLoggingConfigFile() {
        return this.getLoggingConfigurationDirectory() + File.separator + "log4j.properties";
    }

    public String getLoggingConfigurationDirectory() {
        return this.getApplicationConfigurationDirectory() + File.separator + "logging";
    }

    public String getRepoConfigFile() {
        return this.getApplicationConfigurationDirectory() + File.separator + "application_repository.properties";
    }

    public String getApplicationConfigurationDirectory() {
        String config = System.getProperty("application.home");
        if (config != null) {
            return config;
        } else {
            String x = System.getProperty("user.home") + File.separator + ".hfw_app";
            return x;
        }
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("setting properties");
        this.initializeApplicationConfiguration();
        //TODO: generated method body
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initializeDataStorageDirectory() {
        File file = new File(this.getDataStorageDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public String getDataStorageDirectory() {
        return this.getApplicationConfigurationDirectory() + File.separator + "data";
    }
}
