package com.hf.hfw.files.listeners;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.hfw.accounts.events.AccountFileEvent;
import com.hf.hfw.accounts.tasks.OnlineTransactionPotentialMatchesTask;
import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.RegisterManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.OnlineTransaction;
import com.hf.homefinanceshared.RegisterTransaction;
import com.hfw.homefinance.importer.FileAccount;
import com.hfw.homefinance.importer.FileTransaction;
import com.hfw.homefinance.importer.TransactionDataImporter;
import com.hfw.homefinance.importer.TransactionDataImporterFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author pldorrell
 */
public class TransactionFileImportListener implements ApplicationListener<AccountFileEvent> {

    private static final SimpleDateFormat output_format = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger log = Logger.getLogger(TransactionFileImportListener.class);
    private RegisterManager registerManager;
    private AccountManager accountManager;
    private OnlineTransactionPotentialMatchesTask onlineTransactionPotentialMatchesTask;
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public void setOnlineTransactionPotentialMatchesTask(OnlineTransactionPotentialMatchesTask onlineTransactionPotentialMatchesTask) {
        this.onlineTransactionPotentialMatchesTask = onlineTransactionPotentialMatchesTask;
    }

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }
    
    @Override
    public void onApplicationEvent(AccountFileEvent e) {
        if (e.getType().equals(AccountEvent.AccountEventType.UPLOADED_TRANSACTION_FILE)) {
            log.info("processing:" + e.getAccount().getId() + ":" + e.getFileName());
            Account account = this.accountManager.getAccountById(e.getAccount().getId());
            
            TransactionDataImporter tdi = TransactionDataImporterFactory.get(new File(e.getFileName()));
            List<FileAccount> txns = tdi.loadFromFile(e.getFileName());
            List<OnlineTransaction> onlineTransactions = new ArrayList<OnlineTransaction>();
            Date latestTxnDate = null;
            for (FileTransaction f : txns.get(0).getTransactions()) {
                log.info(f.getPayee() + ":" + f.getAmount() + ":" + output_format.format(f.getTxnDate()));
                OnlineTransaction rtxn = new OnlineTransaction();
                rtxn.setCreatedDate(new Date());
                rtxn.setPayee(f.getPayee());
                rtxn.setTxnAmount(Math.abs(f.getAmount()));
                rtxn.setTxnDate(output_format.format(f.getTxnDate()));
                rtxn.setPrimaryAccount(e.getAccount().getId());
                rtxn.setStatusTxt(RegisterTransaction.STATUS_IMPORTED);
                if (f.getAmount() < 0) {
                    rtxn.setCredit(false);
                }
                if (latestTxnDate == null) {
                    latestTxnDate = f.getTxnDate();
                } else if (f.getTxnDate().after(latestTxnDate)) {
                    latestTxnDate = f.getTxnDate();
                }
                
                if ("Checking".equals(account.getAccountType())) {
                    if (f.getCheckNumber() != null) {
                        rtxn.setTxnPersonalRefNumber(f.getCheckNumber());
                    }
                }
                onlineTransactions.add(rtxn);
            }
            if (!onlineTransactions.isEmpty()) {
                this.registerManager.addPendingTransactions(onlineTransactions);
                
                //update the transactionDates on t account
                //Account account = this.accountManager.getAccountById(e.getAccount().getId());
                account.setLastImportActionDate(new Date());
                account.setLastImportedTransactionDate(output_format.format(latestTxnDate));
                this.accountManager.updateAccount(account);
                
                this.onlineTransactionPotentialMatchesTask.execute(account);
            }
            log.info("completed processing:" + e.getAccount().getId() + ":" + e.getFileName());

        }
    }

}
