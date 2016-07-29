package com.hf.hfw.manager;

import com.hf.hfw.api.v1.settings.SettingsBean;
import com.hf.hfw.dao.RepositoryBasedSettingDAO;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author pldorrell
 */
public class SettingsManagerImpl implements SettingsManager {
    private static final Logger log = Logger.getLogger(SettingsManagerImpl.class);
    public static final String TYPE_OF_SETTING_BASICSECURITY = "basicSecurity";
    public static final String TYPE_OF_SETTING_THEME = "theme";
    public static final String TYPE_OF_SETTING_LIMITEDUSERSECURITY="limitedUserSecurity";
    
    private RepositoryBasedSettingDAO repositoryBasedSettingDAO;

    public void setRepositoryBasedSettingDAO(RepositoryBasedSettingDAO repositoryBasedSettingsDAO) {
        this.repositoryBasedSettingDAO = repositoryBasedSettingsDAO;
    }

    @Override
    public SettingsBean getThemeSetting() {
        SettingsBean settingsBean = null;
        try {
            settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_THEME);
        } catch (Exception e) {
            log.error("Unable to get theme. Setting to default",e);
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
        settingsBean.setSettings(settings);
        return settingsBean;
    }
    
    @Override
    public void saveThemeSettings(SettingsBean bean) {
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_THEME, bean);

    }

    @Override
    public SettingsBean getBasicSecuritySetting() {
        SettingsBean settingsBean = null;
        try {
            settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_BASICSECURITY);
        } catch (Exception e) {
            log.error("Unable to get security setting. Using basic.",e);
        }
        
        if (settingsBean == null) {
            settingsBean = this.getDefaultBasicSecuritySetting();
        }
        return settingsBean;
    }

    @Override
    public void saveBasicSecuritySettings(SettingsBean bean) {
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_BASICSECURITY, bean);

    }

    @Override
    public SettingsBean getLimitedSecurityUsers() {        
        SettingsBean settingsBean = null;
        try {
            settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_LIMITEDUSERSECURITY);
        } catch (Exception e) {
            log.error("Unable to get users from settings",e);
        }
        
        if (settingsBean == null) {
            settingsBean = this.getDefaultLimitedUserSecuritySetting();
        }
        return settingsBean;
    }

    @Override
    public void saveLimitedSecurityUsers(SettingsBean bean) {
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_LIMITEDUSERSECURITY, bean);
    }

}
