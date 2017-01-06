/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.accounts.tasks;

import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.RegisterManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.OnlineTransaction;
import com.hf.homefinanceshared.RegisterTransaction;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author pldorrell
 */
public class DeleteProcessedOnlineTransactionsTask {

    private static final Logger log = Logger.getLogger(DeleteProcessedOnlineTransactionsTask.class);

    private RegisterManager registerManager;
    private AccountManager accountManager;

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void runAsTask() {
        log.debug("In runTask");
        this.execute();
        log.debug("Exiting runTask");
    }

    public void execute() {
        log.debug("In execute");
        //get all Pending transactions
        List<Account> accounts = this.accountManager.getAccounts();
        for (Account account : accounts) {
            log.debug("processing OnlineTransacton for Deletion in account" + account.getId());
            List<OnlineTransaction> pendingTransactions = this.registerManager.getPendingTransactions(account);
            for (OnlineTransaction pendingTransaction : pendingTransactions) {
                log.debug("Processing Online Transaction" + pendingTransaction.getId() + " with status of " + pendingTransaction.getStatusTxt());
                if (RegisterTransaction.STATUS_ACCEPTED.equals(pendingTransaction.getStatusTxt())
                        || RegisterTransaction.STATUS_ACCEPTED.equals(pendingTransaction.getStatusTxt()) ) {
                    //do nothing
                    //delete after a certain date
                    log.debug("Deleting OnlineTransaction:" + pendingTransaction.getId());
                    this.registerManager.deleteOnlineTransaction(pendingTransaction);
                } else {

                }

            }

        }
    
        log.debug("Leaving execute");
    }


}
