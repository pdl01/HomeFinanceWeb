/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.lwmds;

import com.hf.hfw.api.v1.settings.SettingsBean;
import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.dao.RepositoryBasedSettingDAO;
import com.hf.hfw.dao.lwmds.converter.AccountConverter;
import com.hf.hfw.dao.lwmds.converter.SettingsConverter;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.DataStore;
import com.hf.lwdatastore.exception.CollectionNotFoundException;
import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author pldorrell
 */
public class RepositoryBasedSettingDAOImpl implements RepositoryBasedSettingDAO {
    private static final Logger log = Logger.getLogger(RepositoryBasedSettingDAOImpl.class);
    private DataStore getDataStore() {
        return ApplicationState.getApplicationState().getDbFactory().getLwDataStore();
    }

    @Override
    public SettingsBean getSetting(String typeOfSetting) {
        try {
            //CollectionObject cObject = getDataStore().getByIndex(typeOfSetting, typeOfSetting, typeOfSetting)getObject("setting", typeOfSetting);
            List<CollectionObject> cObjects = getDataStore().getByIndex("settings", "typeOfSetting", typeOfSetting);
            if (cObjects != null && cObjects.size() > 0) {
                return (new SettingsConverter()).convertFromCollectionObject(cObjects.get(0));
            }
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);
        }
        return null;

    }

    @Override
    public void saveSetting(String typeOfSetting, SettingsBean settingsBean) {
        try {
            getDataStore().putObject("settings", new CollectionObject(settingsBean), new SettingsConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        //return account;

    }
    
}
