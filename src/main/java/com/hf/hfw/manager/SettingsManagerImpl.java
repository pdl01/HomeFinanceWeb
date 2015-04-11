package com.hf.hfw.manager;

import com.hf.hfw.api.v1.settings.SettingsBean;
import com.hf.hfw.dao.RepositoryBasedSettingDAO;
import java.util.HashMap;

/**
 *
 * @author pldorrell
 */
public class SettingsManagerImpl implements SettingsManager {
    public static final String TYPE_OF_SETTING_BASICSECURITY = "basicSecurity";
    public static final String TYPE_OF_SETTING_THEME = "theme";
    private RepositoryBasedSettingDAO repositoryBasedSettingDAO;

    public void setRepositoryBasedSettingDAO(RepositoryBasedSettingDAO repositoryBasedSettingsDAO) {
        this.repositoryBasedSettingDAO = repositoryBasedSettingsDAO;
    }

    @Override
    public SettingsBean getThemeSetting() {
        SettingsBean settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_THEME);
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

    @Override
    public void saveThemeSettings(SettingsBean bean) {
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_THEME, bean);

    }

    @Override
    public SettingsBean getBasicSecuritySetting() {
        SettingsBean settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_BASICSECURITY);
        if (settingsBean == null) {
            settingsBean = this.getDefaultBasicSecuritySetting();
        }
        return settingsBean;
    }

    @Override
    public void saveBasicSecuritySettings(SettingsBean bean) {
        this.repositoryBasedSettingDAO.saveSetting(TYPE_OF_SETTING_BASICSECURITY, bean);

    }

}
