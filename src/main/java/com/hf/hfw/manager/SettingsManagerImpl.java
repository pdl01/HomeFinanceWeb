package com.hf.hfw.manager;

import com.hf.hfw.accounts.events.SettingsEvent;
import com.hf.hfw.api.v1.settings.SettingsBean;
import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.dao.RepositoryBasedSettingDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author pldorrell
 */
public class SettingsManagerImpl implements SettingsManager {

    private static final Logger log = Logger.getLogger(SettingsManagerImpl.class);
    public static final String TYPE_OF_SETTING_BASICSECURITY = "basicSecurity";
    public static final String TYPE_OF_SETTING_THEME = "theme";
    public static final String TYPE_OF_SETTING_LIMITEDUSERSECURITY = "limitedUserSecurity";
    public static final String TYPE_OF_SETTING_EMAILSERVICE = "emailServer";
    public static final String TYPE_OF_SETTING_GENERAL = "generalProp";

    protected Map<String, SettingsBean> settings;

    private RepositoryBasedSettingDAO repositoryBasedSettingDAO;

    public void setRepositoryBasedSettingDAO(RepositoryBasedSettingDAO repositoryBasedSettingsDAO) {
        this.repositoryBasedSettingDAO = repositoryBasedSettingsDAO;
    }

    @Override
    public SettingsBean getThemeSetting() {
        SettingsBean settingsBean = null;
        try {
            settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_THEME);
            //settingsBean = this.settings.get(TYPE_OF_SETTING_THEME);
        } catch (Exception e) {
            log.error("Unable to get theme. Setting to default", e);
        }

        if (settingsBean == null) {
            return this.getDefaultThemeSetting();
        }
        return settingsBean;
    }

    private SettingsBean getDefaultThemeSetting() {
        SettingsBean settingsBean = new SettingsBean();
        settingsBean.setTypeOfSetting(TYPE_OF_SETTING_THEME);
        HashMap<String, String> settings = new HashMap<String, String>();
        settings.put("theme", "default");
        settingsBean.setSettings(settings);
        return settingsBean;
    }

    private SettingsBean getDefaultBasicSecuritySetting() {
        SettingsBean settingsBean = new SettingsBean();
        settingsBean.setTypeOfSetting(TYPE_OF_SETTING_BASICSECURITY);
        HashMap<String, String> settings = new HashMap<String, String>();
        settings.put("enabled", "false");
        settings.put("password", "admin");
        settingsBean.setSettings(settings);
        return settingsBean;
    }

    private SettingsBean getDefaultLimitedUserSecuritySetting() {
        SettingsBean settingsBean = new SettingsBean();
        settingsBean.setTypeOfSetting(TYPE_OF_SETTING_LIMITEDUSERSECURITY);
        HashMap<String, String> settings = new HashMap<String, String>();
        settings.put("user1name", "user1");
        settings.put("user1password", "admin");
        settings.put("user1email", "");
        settingsBean.setSettings(settings);
        return settingsBean;
    }

    @Override
    public void saveThemeSettings(SettingsBean bean) {
        bean.setId(TYPE_OF_SETTING_THEME);
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_THEME, bean);
        //this.addSettingsBeanToSystem(bean);
        //fireSettingsEvent(bean, SettingsEvent.SettingsEventType.MODIFIED);

    }

    @Override
    public SettingsBean getBasicSecuritySetting() {
        SettingsBean settingsBean = null;
        try {
            //settingsBean = this.settings.get(TYPE_OF_SETTING_BASICSECURITY);
            settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_BASICSECURITY);
        } catch (Exception e) {
            log.error("Unable to get security setting. Using basic.", e);
        }

        if (settingsBean == null) {
            settingsBean = this.getDefaultBasicSecuritySetting();
        }
        return settingsBean;
    }

    @Override
    public void saveBasicSecuritySettings(SettingsBean bean) {
        //this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_BASICSECURITY, bean);
        bean.setId(TYPE_OF_SETTING_BASICSECURITY);
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_BASICSECURITY, bean);
        //this.addSettingsBeanToSystem(bean);
        //fireSettingsEvent(bean, SettingsEvent.SettingsEventType.MODIFIED);

    }

    @Override
    public SettingsBean getLimitedSecurityUsers() {
        SettingsBean settingsBean = null;
        try {
            settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_LIMITEDUSERSECURITY);
            //settingsBean = this.settings.get(TYPE_OF_SETTING_LIMITEDUSERSECURITY);
        } catch (Exception e) {
            log.error("Unable to get users from settings", e);
        }

        if (settingsBean == null) {
            settingsBean = this.getDefaultLimitedUserSecuritySetting();
        }
        return settingsBean;
    }

    @Override
    public void saveLimitedSecurityUsers(SettingsBean bean) {
        //this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_LIMITEDUSERSECURITY, bean);
        //log.info(bean.getId());
        bean.setId(TYPE_OF_SETTING_LIMITEDUSERSECURITY);
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_LIMITEDUSERSECURITY, bean);
        //this.addSettingsBeanToSystem(bean);
        //fireSettingsEvent(bean, SettingsEvent.SettingsEventType.MODIFIED);

    }

    protected void fireSettingsEvent(SettingsBean _bean, SettingsEvent.SettingsEventType _type) {
        ApplicationContext appContext = ApplicationState.getApplicationState().getCtx();
        SettingsEvent event = new SettingsEvent(appContext, _bean, _type);
        appContext.publishEvent(event);
    }

    @Override
    public SettingsBean addSettingsBeanToSystem(SettingsBean settingsBean) {
        if (this.settings == null) {
            this.settings = new HashMap<>();
        }
        this.settings.put(settingsBean.getId(), settingsBean);
        return settingsBean;
    }

    @Override
    public List<SettingsBean> getAllSettings() {
        ArrayList<SettingsBean> allSettings = new ArrayList<>();
        allSettings.add(this.getBasicSecuritySetting());
        allSettings.add(this.getLimitedSecurityUsers());
        allSettings.add(this.getThemeSetting());
        allSettings.add(this.getEmailServerSettings());
        return allSettings;
    }

    @Override
    public SettingsBean getEmailServerSettings() {
        SettingsBean settingsBean = null;
        try {
            settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_EMAILSERVICE);
            //settingsBean = this.settings.get(TYPE_OF_SETTING_LIMITEDUSERSECURITY);
        } catch (Exception e) {
            log.error("Unable to get emailService from settings", e);
        }

        if (settingsBean == null) {
            settingsBean = this.getDefaultEmailServiceSettings();
        }
        return settingsBean;
    }

    @Override
    public void saveEmailServerSettings(SettingsBean bean) {
        bean.setId(TYPE_OF_SETTING_EMAILSERVICE);
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_EMAILSERVICE, bean);
    }
    private SettingsBean getDefaultEmailServiceSettings() {
        SettingsBean settingsBean = new SettingsBean();
        settingsBean.setTypeOfSetting(TYPE_OF_SETTING_EMAILSERVICE);
        HashMap<String, String> settings = new HashMap<String, String>();
        settings.put(EmailManager.SETTING_NAME_SMTP_SERVER, "");
        settings.put(EmailManager.SETTING_NAME_SMTP_PORT, "");
        settings.put(EmailManager.SETTING_NAME_SMTP_AUTHENTICATION_REQ, "");
        settings.put(EmailManager.SETTING_NAME_SMTP_SECURITY,"none");
        settings.put(EmailManager.SETTING_NAME_SMTP_USERNAME, "");
        settings.put(EmailManager.SETTING_NAME_SMTP_PASSWORD, "");
        settings.put(EmailManager.SETTING_NAME_SMTP_FROM_EMAIL,"HFW.App");
        
        settingsBean.setSettings(settings);
        return settingsBean;
    }

    @Override
    public void saveGeneralProperties(SettingsBean bean) {
        bean.setId(TYPE_OF_SETTING_GENERAL);
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_GENERAL, bean);    }

    @Override
    public SettingsBean getGeneralProperties() {
        SettingsBean settingsBean = null;
        try {
            settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_GENERAL);
        } catch (Exception e) {
            log.error("Unable to get general settings from settings", e);
        }


        return settingsBean;
    }

    @Override
    public SettingsBean addGeneralProperty(String property, String value) {
        SettingsBean settingsBean = this.getGeneralProperties();
        HashMap<String, String> settings = null;
        if (settingsBean == null) {
            settingsBean = new SettingsBean();
            settingsBean.setTypeOfSetting(TYPE_OF_SETTING_GENERAL);
            settings = new HashMap<String, String>();
            settingsBean.setSettings(settings);
       
        }
        settingsBean.getSettings().put(property, value);
        this.saveGeneralProperties(settingsBean);
        return settingsBean;            
        
    }
}
