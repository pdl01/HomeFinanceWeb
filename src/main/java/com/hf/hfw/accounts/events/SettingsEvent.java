
package com.hf.hfw.accounts.events;

import com.hf.hfw.api.v1.settings.SettingsBean;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author pldor
 */
public class SettingsEvent extends ApplicationEvent {
    private SettingsEventType type;
    private SettingsBean settingsBean;
    
    public enum SettingsEventType {
        MODIFIED(1);
        private int code;

        private SettingsEventType(int _code) {
            this.code = _code;
        }

        public int getCode() {
            return code;
        }
    }
    public SettingsEvent(Object _source,SettingsBean _settingsBean,SettingsEvent.SettingsEventType _type) {
	super(_source);
        this.settingsBean = _settingsBean;
        this.type = _type;
    }

    public SettingsEventType getType() {
        return type;
    }

    public void setType(SettingsEventType type) {
        this.type = type;
    }

    public SettingsBean getSettingsBean() {
        return settingsBean;
    }

    public void setSettingsBean(SettingsBean settingsBean) {
        this.settingsBean = settingsBean;
    }
    
}
