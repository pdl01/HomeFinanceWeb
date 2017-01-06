
package com.hf.hfw.manager;

import com.hf.hfw.api.v1.settings.SettingsBean;

/**
 *
 * @author pldor
 */
public interface EmailManager {
    public final static String SETTING_NAME_SMTP_SERVER = "SMTPserver";
    public final static String SETTING_NAME_SMTP_PORT = "SMTPPort";
    public final static String SETTING_NAME_SMTP_AUTHENTICATION_REQ = "SMTPRequiresAuth";
    public final static String SETTING_NAME_SMTP_SECURITY = "SMTPSecurity";
    public final static String SETTING_NAME_SMTP_USERNAME = "SMTPUsername";
    public final static String SETTING_NAME_SMTP_PASSWORD = "SMTPPassword";
    public final static String SETTING_NAME_SMTP_FROM_EMAIL = "SMTPEmailFrom";  
    
    public String sendTestEmail(String emailAddress);
    public String sendEmail(String emailAddress,String subject, String message);
    public boolean validateEmailSettings(SettingsBean settingsBean);
}
