package com.hf.hfw.api.v1.settings;

import com.hf.hfw.application.ApplicationRepositoryConfig;
import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.application.ConfigurationDirectoryService;
import com.hf.hfw.manager.EmailManager;
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
    private EmailManager emailManager;

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
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
        } else if ("limitedusersecurity".equals(typeOfSettings)) {
            settingsBean = this.settingsManager.getLimitedSecurityUsers();
        } else if ("email".equals(typeOfSettings)) {
            settingsBean = this.settingsManager.getEmailServerSettings();
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
            applicationRepositoryConfig.setType(settingsbean.getSettings().get("type"));
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
        } else if ("limitedusersecurity".equals(typeOfSettings)) {
            SettingsBean newsettingsBean = this.settingsManager.getLimitedSecurityUsers();
            newsettingsBean.settings.put("user1name", settingsbean.getSettings().get("user1name"));
            newsettingsBean.settings.put("user1password", settingsbean.getSettings().get("user1password"));
            newsettingsBean.settings.put("user1email", settingsbean.getSettings().get("user1email"));
            newsettingsBean.settings.put("user2name", settingsbean.getSettings().get("user2name"));
            newsettingsBean.settings.put("user2password", settingsbean.getSettings().get("user2password"));
            newsettingsBean.settings.put("user2email", settingsbean.getSettings().get("user2email"));
            newsettingsBean.settings.put("user3name", settingsbean.getSettings().get("user3name"));
            newsettingsBean.settings.put("user3password", settingsbean.getSettings().get("user3password"));
            newsettingsBean.settings.put("user3email", settingsbean.getSettings().get("user3email"));
            this.settingsManager.saveLimitedSecurityUsers(newsettingsBean);
        } else if ("email".equals(typeOfSettings)) {
            SettingsBean newSettingsBean = this.settingsManager.getEmailServerSettings();
            newSettingsBean.settings.put(EmailManager.SETTING_NAME_SMTP_SERVER, settingsbean.getSettings().get(EmailManager.SETTING_NAME_SMTP_SERVER));
            newSettingsBean.settings.put(EmailManager.SETTING_NAME_SMTP_PORT, settingsbean.getSettings().get(EmailManager.SETTING_NAME_SMTP_PORT));
            newSettingsBean.settings.put(EmailManager.SETTING_NAME_SMTP_AUTHENTICATION_REQ, settingsbean.getSettings().get(EmailManager.SETTING_NAME_SMTP_AUTHENTICATION_REQ));
            newSettingsBean.settings.put(EmailManager.SETTING_NAME_SMTP_SECURITY, settingsbean.getSettings().get(EmailManager.SETTING_NAME_SMTP_SECURITY));
            newSettingsBean.settings.put(EmailManager.SETTING_NAME_SMTP_USERNAME, settingsbean.getSettings().get(EmailManager.SETTING_NAME_SMTP_USERNAME));
            newSettingsBean.settings.put(EmailManager.SETTING_NAME_SMTP_PASSWORD, settingsbean.getSettings().get(EmailManager.SETTING_NAME_SMTP_PASSWORD));
            newSettingsBean.settings.put(EmailManager.SETTING_NAME_SMTP_FROM_EMAIL, settingsbean.getSettings().get(EmailManager.SETTING_NAME_SMTP_FROM_EMAIL));
            this.settingsManager.saveEmailServerSettings(newSettingsBean);
        }
        return settingsbean;
    }

    @Override
    public String validateEmailSettings(SettingsBean settingsBean) {
        boolean validSettings = this.emailManager.validateEmailSettings(settingsBean);
        if (validSettings) {
            return "VALID_SETTINGS";
        } else {
            return "INVALID_SETTINGS";
        }
    }

    @Override
    public String sendTestEmail(String email) {
        String emailResponse = this.emailManager.sendTestEmail(email);
        return emailResponse;
    }

}
