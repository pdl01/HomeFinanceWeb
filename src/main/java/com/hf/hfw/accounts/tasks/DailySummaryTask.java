package com.hf.hfw.accounts.tasks;

import com.hf.hfw.api.v1.settings.SettingsBean;
import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.EmailManager;
import com.hf.hfw.manager.RegisterManager;
import com.hf.hfw.manager.ScheduledTransactionManager;
import com.hf.hfw.manager.SettingsManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.ScheduledTransaction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author pldor
 */
public class DailySummaryTask {
    private static final Logger log = Logger.getLogger(DailySummaryTask.class);
    private static final SimpleDateFormat transactionDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private AccountManager accountManager;
    private ScheduledTransactionManager scheduledTransactionManager;
    private SettingsManager settingsManager;
    private EmailManager emailManager;

    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
    }
    
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setScheduledTransactionManager(ScheduledTransactionManager scheduledTransactionManager) {
        this.scheduledTransactionManager = scheduledTransactionManager;
    }

    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }
    
    public void runAsTask() {
        log.info("in runTask");
        StringBuilder str = new StringBuilder();
        List<Account> accounts = this.accountManager.getAccounts();
        str.append("<b>Balances</b>"+"<br>");
        for (Account account:accounts) {
            str.append(account.getName()+":"+account.getCurrentBalance()+"<br>");
        }
        str.append("<p>");
        str.append("Upcoming Transactions"+"<br>");
        String todaysDate = transactionDateFormatter.format(new Date());
        for (Account account:accounts) {
            List<ScheduledTransaction> txns = this.scheduledTransactionManager.getLatestUpcomingTransactions(account);
            if (txns != null) {
                for (ScheduledTransaction txn:txns) {
                    if (txn.getTxnDate().equals(todaysDate)){
                        str.append("Account:"+account.getName()+" "+txn.getTxnAmount());
                    }
                }
            }
        }
        
        SettingsBean settingsBean = this.settingsManager.getLimitedSecurityUsers();
        String user1Email = settingsBean.getSettings().get("user1email");
        String user2Email = settingsBean.getSettings().get("user2email");
        String user3Email = settingsBean.getSettings().get("user3email");
        
        String dailySummarySubject = "Daily Summary for " + todaysDate;
        this.sendEmail(user1Email,dailySummarySubject,str.toString());
        this.sendEmail(user2Email,dailySummarySubject,str.toString());
        this.sendEmail(user3Email,dailySummarySubject,str.toString());
        
    }

    private void sendEmail(String emailAddress,String subject,String body) {
       if (emailAddress != null && !"".equals(emailAddress)){
            this.emailManager.sendEmail(emailAddress, subject, body);
        } 
    }
}
