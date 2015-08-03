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
 * @author pldorrell get all Pending transactions that are in imported status
 * and have not been processed for matches call the match routine if there are
 * any transactions returned set the matches flag to true; set the
 * processedForMatches flag to true save the pending transaction
 */
public class OnlineTransactionPotentialMatchesTask {

    private static final Logger log = Logger.getLogger(OnlineTransactionPotentialMatchesTask.class);

    RegisterManager registerManager;
    AccountManager accountManager;

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void runAsTask() {
        log.debug("Entering runAsTask");
        this.execute();
        log.debug("Exiting runAsTask");
    }

    public void execute() {
        log.debug("Entering execute");
        List<Account> accounts = this.accountManager.getAccounts();
        for (Account account : accounts) {
            this.execute(account);

        }
        log.debug("Exiting execute");
    }

    public void execute(Account account) {
        log.debug("Entering execute for account");
        List<OnlineTransaction> txns = this.registerManager.getPendingTransactions(account);
        for (OnlineTransaction txn : txns) {
            if (!txn.isProcessedForMatches()) {
                List<RegisterTransaction> regTxns = this.registerManager.matchTransaction(txn);
                if (regTxns != null && regTxns.size() > 0) {
                    txn.setMatches(true);
                }
                txn.setProcessedForMatches(true);
                this.registerManager.addPendingTransactions(txn);
            }
        }
        log.debug("Exiting execute for account");
    }
}
