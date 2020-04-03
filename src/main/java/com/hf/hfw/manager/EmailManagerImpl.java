package com.hf.hfw.manager;

import com.hf.hfw.api.v1.settings.SettingsBean;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author pldor
 */
public class EmailManagerImpl implements EmailManager {

    private static final Logger log = LogManager.getLogger(SettingsManagerImpl.class);

    private SettingsManager settingsManager;

    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public String sendTestEmail(String emailAddress) {
        String emailResponse = this.sendEmail(emailAddress, "test message from HFW", "This message is a test");
        return emailResponse;
    }

    @Override
    public String sendEmail(String emailAddress, String subject, String message) {
        SettingsBean emailSettings = this.settingsManager.getEmailServerSettings();

        String smtpHostServer = emailSettings.getSettings().get("SMTPserver");

        //Properties props = System.getProperties();

        //props.put("mail.smtp.host", smtpHostServer);
        //props.put("mail.smtp.port", emailSettings.getSettings().get("SMTPPort"));
        String fromEmail = emailSettings.getSettings().get(EmailManager.SETTING_NAME_SMTP_FROM_EMAIL);//"fromUser@here.com";
        String replyEmail = emailSettings.getSettings().get(EmailManager.SETTING_NAME_SMTP_FROM_EMAIL);
        Session session = this.setUpSession(emailSettings);
        //Session session = Session.getInstance(props, null);
        String emailResponse = this.sendEmail(session,  fromEmail, replyEmail,emailAddress, subject, message);
        return emailResponse;
    }

    private String sendEmail(Session session, String fromEmail, String replyToEmail, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail));

            msg.setReplyTo(InternetAddress.parse(replyToEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");
            msg.setContent(body, "text/html");
            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);
            log.info("EMail Sent Successfully!");
            return "Email Sent";

        } catch (Exception e) {
            log.error("Error when sending email", e);
            return "Email Exception Occurred" + e.getMessage();
        }
    }

    @Override
    public boolean validateEmailSettings(SettingsBean settingsBean) {
        //try to connect to the server at the specified port
        Session session = this.setUpSession(settingsBean);
        if (session == null) {
            return false;
        }
        return true;
    }

    private Session setUpSession(SettingsBean settingsBean) {
        Properties props = new Properties();

        props.put("mail.smtp.host", settingsBean.getSettings().get(EmailManager.SETTING_NAME_SMTP_SERVER)); //SMTP Host
        props.put("mail.smtp.port", settingsBean.getSettings().get(EmailManager.SETTING_NAME_SMTP_PORT));    
        if ("SSL".equals(settingsBean.getSettings().get(EmailManager.SETTING_NAME_SMTP_SECURITY))) {
            props.put("mail.smtp.socketFactory.port", settingsBean.getSettings().get(EmailManager.SETTING_NAME_SMTP_PORT)); //SSL Port
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class 
        } else if ("TLS".equals(settingsBean.getSettings().get(EmailManager.SETTING_NAME_SMTP_SECURITY))) {

        }
        Authenticator auth = null;
        if ("true".equals(settingsBean.getSettings().get(EmailManager.SETTING_NAME_SMTP_AUTHENTICATION_REQ))) {
            props.put("mail.smtp.auth", "true");
            auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(settingsBean.getSettings().get(EmailManager.SETTING_NAME_SMTP_USERNAME), settingsBean.getSettings().get(EmailManager.SETTING_NAME_SMTP_PASSWORD));
                }
            };
        } else {
            props.put("mail.smtp.auth", "false");
        }

        Session session = Session.getInstance(props, auth);
        return session;
    }
}
