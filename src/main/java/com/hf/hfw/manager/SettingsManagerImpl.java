package com.hf.hfw.manager;

import com.hf.hfw.api.v1.settings.SettingsBean;
import com.hf.hfw.dao.RepositoryBasedSettingDAO;

/**
 *
 * @author pldorrell
 */
public class SettingsManagerImpl implements SettingsManager {
    private RepositoryBasedSettingDAO repositoryBasedSettingsDAO;

    public void setRepositoryBasedSettingsDAO(RepositoryBasedSettingDAO repositoryBasedSettingsDAO) {
        this.repositoryBasedSettingsDAO = repositoryBasedSettingsDAO;
    }
    
    @Override
    public SettingsBean getThemeSetting() {
        return this.repositoryBasedSettingsDAO.getSetting("theme");
    }

    @Override
    public void saveThemeSettings(SettingsBean bean) {
        this.repositoryBasedSettingsDAO.saveSetting("theme", bean);
        
    }

    @Override
    public SettingsBean getBasicSecuritySetting() {
        return this.repositoryBasedSettingsDAO.getSetting("basicsecurity");
    }

    @Override
    public void saveBasicSecuritySettings(SettingsBean bean) {
        this.repositoryBasedSettingsDAO.saveSetting("basicsecurity", bean);

    }
    
}
