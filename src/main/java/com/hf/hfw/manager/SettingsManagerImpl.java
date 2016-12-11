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

    protected Map<String, SettingsBean> settings;

    private RepositoryBasedSettingDAO repositoryBasedSettingDAO;

    public void setRepositoryBasedSettingDAO(RepositoryBasedSettingDAO repositoryBasedSettingsDAO) {
        this.repositoryBasedSettingDAO = repositoryBasedSettingsDAO;
    }

    @Override
    public SettingsBean getThemeSetting() {
        SettingsBean settingsBean = null;
        try {
            //settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_THEME);
            settingsBean = this.settings.get(TYPE_OF_SETTING_THEME);
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
        settingsBean.setSettings(settings);
        return settingsBean;
    }

    @Override
    public void saveThemeSettings(SettingsBean bean) {
        bean.setId(TYPE_OF_SETTING_THEME);
        this.addSettingsBeanToSystem(bean);
        fireSettingsEvent(bean, SettingsEvent.SettingsEventType.MODIFIED);

    }

    @Override
    public SettingsBean getBasicSecuritySetting() {
        SettingsBean settingsBean = null;
        try {
            settingsBean = this.settings.get(TYPE_OF_SETTING_BASICSECURITY);
            //settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_BASICSECURITY);
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
        this.addSettingsBeanToSystem(bean);
        fireSettingsEvent(bean, SettingsEvent.SettingsEventType.MODIFIED);

    }

    @Override
    public SettingsBean getLimitedSecurityUsers() {
        SettingsBean settingsBean = null;
        try {
            //settingsBean = this.repositoryBasedSettingDAO.getSetting(TYPE_OF_SETTING_LIMITEDUSERSECURITY);
            settingsBean = this.settings.get(TYPE_OF_SETTING_LIMITEDUSERSECURITY);
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
        log.info(bean.getId());
        bean.setId(TYPE_OF_SETTING_LIMITEDUSERSECURITY);
        this.addSettingsBeanToSystem(bean);
        fireSettingsEvent(bean, SettingsEvent.SettingsEventType.MODIFIED);

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
        ArrayList<SettingsBean> localSettings = new ArrayList<>();
        if (this.settings == null || this.settings.isEmpty()) {
            return localSettings;
        }
        for (String key : this.settings.keySet()) {
            localSettings.add(this.settings.get(key));
        }
        return localSettings;

    }

}
