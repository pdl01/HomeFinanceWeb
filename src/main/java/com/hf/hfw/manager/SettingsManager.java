package com.hf.hfw.manager;

import com.hf.hfw.api.v1.settings.SettingsBean;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface SettingsManager {
    public SettingsBean getThemeSetting();
    public void saveThemeSettings(SettingsBean bean);
    public SettingsBean getBasicSecuritySetting();
    public void saveBasicSecuritySettings(SettingsBean bean);
    public SettingsBean getLimitedSecurityUsers();
    public void saveLimitedSecurityUsers(SettingsBean bean);
    
    public SettingsBean addSettingsBeanToSystem(SettingsBean settingsBean); 
    public List<SettingsBean> getAllSettings();
}
