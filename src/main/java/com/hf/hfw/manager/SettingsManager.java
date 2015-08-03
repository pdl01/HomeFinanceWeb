/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.api.v1.settings.SettingsBean;

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
}
