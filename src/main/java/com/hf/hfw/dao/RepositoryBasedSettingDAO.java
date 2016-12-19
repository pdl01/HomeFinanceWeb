package com.hf.hfw.dao;

import com.hf.hfw.api.v1.settings.SettingsBean;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface RepositoryBasedSettingDAO {
    public SettingsBean getSetting(String typeOfSetting);
    public void saveSetting(String typeOfSetting,SettingsBean settingsBean);
}
