package com.hf.hfw.api.v1.settings;

import com.hf.hfw.application.ApplicationRepositoryConfig;
import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.application.ConfigurationDirectoryService;
import com.hf.hfw.manager.SettingsManager;
import java.util.HashMap;
import javax.ws.rs.Path;

/**
 *
 * @author pldorrell
 */
@Path("/settings")
public class SettingsServiceImpl implements SettingsService {

    private ConfigurationDirectoryService configurationDirectoryService;
    private SettingsManager settingsManager;

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public ConfigurationDirectoryService getConfigurationDirectoryService() {
        return configurationDirectoryService;
    }

    public void setConfigurationDirectoryService(ConfigurationDirectoryService configurationDirectoryService) {
        this.configurationDirectoryService = configurationDirectoryService;
    }

    @Override
    public SettingsBean getSettings(String typeOfSettings) {
        SettingsBean settingsBean = new SettingsBean();
        if ("db".equals(typeOfSettings)) {
            settingsBean.setTypeOfSetting("db");
            HashMap<String, String> settings = new HashMap<String, String>();
            ApplicationRepositoryConfig repoConfig = ApplicationState.getApplicationState().getApplicationRepositoryConfig();
            settings.put("host", repoConfig.getHost());
            settings.put("username", repoConfig.getUsername());
            settings.put("password", repoConfig.getPassword());
            settings.put("port", repoConfig.getPort());
            settings.put("database", repoConfig.getDb());
            settings.put("type", repoConfig.getType());
            settingsBean.setSettings(settings);
        } else if ("theme".equals(typeOfSettings)) {
            settingsBean = this.settingsManager.getThemeSetting();
        } else if ("basicsecurity".equals(typeOfSettings)) {
            settingsBean = this.settingsManager.getBasicSecuritySetting();
        }
        return settingsBean;

    }

    @Override
    public SettingsBean saveSettings(String typeOfSettings, SettingsBean settingsbean) {
        if ("db".equals(typeOfSettings)) {
            ApplicationRepositoryConfig applicationRepositoryConfig = new ApplicationRepositoryConfig();
            applicationRepositoryConfig.setDb(settingsbean.getSettings().get("database"));
            applicationRepositoryConfig.setHost(settingsbean.getSettings().get("host"));
            applicationRepositoryConfig.setPort(settingsbean.getSettings().get("port"));
            applicationRepositoryConfig.setUsername(settingsbean.getSettings().get("username"));
            applicationRepositoryConfig.setPassword(settingsbean.getSettings().get("password"));
            applicationRepositoryConfig.setType("mongo");
            ApplicationRepositoryConfig.saveToConfig(applicationRepositoryConfig, this.getConfigurationDirectoryService().getRepoConfigFile());
        } else if ("theme".equals(typeOfSettings)) {
            SettingsBean settingsBean = this.settingsManager.getThemeSetting();
            settingsBean.settings.put("theme", settingsbean.getSettings().get("theme"));
            this.settingsManager.saveThemeSettings(settingsbean);
        } else if ("basicsecurity".equals(typeOfSettings)) {
            SettingsBean settingsBean = this.settingsManager.getBasicSecuritySetting();
            settingsBean.settings.put("enabled", settingsbean.getSettings().get("enabled"));
            settingsBean.settings.put("password", settingsbean.getSettings().get("password"));
            this.settingsManager.saveBasicSecuritySettings(settingsbean);
        }
        return settingsbean;
    }

}
