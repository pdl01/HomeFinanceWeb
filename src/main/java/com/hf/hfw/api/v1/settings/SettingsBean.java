/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1.settings;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pldorrell
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SettingsBean {
    protected String typeOfSetting;
    protected String validationMessage;
    protected HashMap<String,String> settings;

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public String getTypeOfSetting() {
        return typeOfSetting;
    }

    public void setTypeOfSetting(String typeOfSetting) {
        this.typeOfSetting = typeOfSetting;
    }

    public HashMap<String, String> getSettings() {
        return settings;
    }

    public void setSettings(HashMap<String, String> settings) {
        this.settings = settings;
    }
    
}
