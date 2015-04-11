/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.mongo;

import com.hf.hfw.api.v1.settings.SettingsBean;
import com.hf.hfw.dao.RepositoryBasedSettingDAO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author pldorrell
 */
public class RepositoryBasedSettingDAOImpl extends AbstractMongoDAO implements RepositoryBasedSettingDAO {

    @Override
    public SettingsBean getSetting(String typeOfSetting) {
        Query settingsQuery = new Query(Criteria.where("typeOfSetting").is(typeOfSetting));

        return this.getMongoTemplate().findOne(settingsQuery, SettingsBean.class,"Settings");
    }

    @Override
    public void saveSetting(String typeOfSetting, SettingsBean settingsBean) {
        SettingsBean currentSettingsBean = this.getSetting(typeOfSetting);
        if (currentSettingsBean != null) {
            currentSettingsBean.setSettings(settingsBean.getSettings());
        } else {
            currentSettingsBean = settingsBean;
        }
        this.getMongoTemplate().save(currentSettingsBean,"Settings");

    }

}
