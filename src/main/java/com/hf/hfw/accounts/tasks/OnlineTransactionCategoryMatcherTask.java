package com.hf.hfw.accounts.tasks;

import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.RegisterManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.OnlineTransaction;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author pldorrell
        //get all Pending transactions that are in imported status and have not been processed
        //look in the account for transactions that have the payee or amount
        //get the categories and amounts from the original and copy them into the pending transaction
        //save the pending transaction



 */
public class OnlineTransactionCategoryMatcherTask {
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
        this.execute();
    }
    public void execute() {
        List<Account> accounts = this.accountManager.getAccounts();
        for (Account account: accounts) {
            this.execute(account);
        }
        
        
    }
    public void execute(Account account) {
            List<OnlineTransaction> txns = this.registerManager.getPendingTransactions(account);
            for (OnlineTransaction txn:txns) {
                if (!txn.isProcessedForCategories()) {
                    //List<RegisterTransaction> regTxns = this.registerManager.matchTransaction(txn);
                    //if (regTxns != null && regTxns.size() > 0) {
                    //    txn.setMatches(true);
                    //}
                    //get the latest transaction with the same name
                    
                    txn.setProcessedForCategories(true);
                    this.registerManager.addPendingTransactions(txn);
                }
            }
        
    }
     
    
    
}
