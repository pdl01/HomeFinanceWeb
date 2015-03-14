/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao;

import com.hf.hfw.api.v1.settings.SettingsBean;

/**
 *
 * @author pldorrell
 */
public interface RepositoryBasedSettingDAO {
    public SettingsBean getSetting(String typeOfSetting);
    public void saveSetting(String typeOfSetting,SettingsBean settingsBean);
}
