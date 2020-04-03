package com.hf.hfw.api.v1;

import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.ScheduledTransactionManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.ScheduledTransaction;
import java.util.List;
import javax.ws.rs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pldorrell
 */
@Path("/schedule")
public class ScheduledTransactionServiceImpl implements ScheduledTransactionService {

    private static final Logger log = LogManager.getLogger(ScheduledTransactionServiceImpl.class);
    private ScheduledTransactionManager scheduledTransactionManager;
    private AccountManager accountManager;

    public ScheduledTransactionManager getScheduledTransactionManager() {
        return scheduledTransactionManager;
    }

    public void setScheduledTransactionManager(ScheduledTransactionManager scheduledTransactionManager) {
        this.scheduledTransactionManager = scheduledTransactionManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactions(String accountId) {
        Account account = this.accountManager.getAccountById(accountId);
        return this.scheduledTransactionManager.getUpcomingTransactions(account);
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ScheduledTransaction saveTransaction(ScheduledTransaction transaction) throws Exception {

        if (transaction.getId() != null) {
            ScheduledTransaction retrievedtxn = this.scheduledTransactionManager.getById(transaction.getId());

            boolean isRebuildNeeded = this.isRebuildEvent(retrievedtxn, transaction);

            //transaction.setCreatedDate(retrievedtxn.getCreatedDate());
            //transaction.setLastModifiedDate(retrievedtxn.getLastModifiedDate());
            retrievedtxn.setTxnDate(transaction.getTxnDate());
            retrievedtxn.setPayee(transaction.getPayee());
            retrievedtxn.setCategorySplits(transaction.getCategorySplits());
            retrievedtxn.setTxnAmount(transaction.getTxnAmount());
            retrievedtxn.setPrimaryAccount(transaction.getPrimaryAccount());
            retrievedtxn.setCredit(transaction.isCredit());
            retrievedtxn.setTxnPersonalRefNumber(transaction.getTxnPersonalRefNumber());
            retrievedtxn.setScheduledDate(transaction.getScheduledDate());

            retrievedtxn = this.scheduledTransactionManager.updateTransaction(retrievedtxn);
            if (isRebuildNeeded) {
                this.scheduledTransactionManager.rebuildUpcomingTransactions(retrievedtxn);
            }
            return retrievedtxn;
        } else {
            return this.scheduledTransactionManager.createTransaction(transaction);
        }
    }

    /*
    *rebuild events
    *   frequency change
    *   amount change
    *   payee change
    *   end date
    *   category splits
    *   number of occurrences
    *   change of scheduling type (end date to number of occurrences)
     */
    private boolean isRebuildEvent(ScheduledTransaction preTransaction, ScheduledTransaction postTransaction) {
        boolean isRebuild = false;
        if (preTransaction.getOriginalTransactionId() != null) {
            //this is a scheduled txn; do not rebuild this
            isRebuild = false;
        } else if (!preTransaction.getEndDate().equalsIgnoreCase(postTransaction.getEndDate())) {
            isRebuild = true;
        } else if (!preTransaction.getPayee().equalsIgnoreCase(postTransaction.getPayee())) {
            isRebuild = true;
        } else if (preTransaction.getFrequency() != postTransaction.getFrequency()) {
            isRebuild = true;
        } else if (preTransaction.getNumberOfOccurrences() != postTransaction.getNumberOfOccurrences()) {
            isRebuild = true;
        } else if (preTransaction.isCredit() != postTransaction.isCredit()) {
            isRebuild = true;
        } else if (preTransaction.getTxnAmount() != postTransaction.getTxnAmount()) {
            isRebuild = true;
        }
        //TODO: check for change between scheduling modes; number of occurrences to end date and vice versa
        //TODO: check for category split changes

        return isRebuild;
    }

    @Override
    public void skipTransaction(String transactionId) throws Exception {
        ScheduledTransaction txn = this.scheduledTransactionManager.getById(transactionId);
        if (txn != null && txn.getOriginalTransactionId() != null) {
            if (!ScheduledTransaction.STATUS_PAID.equalsIgnoreCase(txn.getStatusTxt())) {
                txn.setStatusTxt(ScheduledTransaction.STATUS_SKIPPED);
                this.scheduledTransactionManager.updateTransaction(txn);
            }
        }

    }

    @Override
    public void deleteTransaction(String transactionId) {
        ScheduledTransaction txn = this.scheduledTransactionManager.getById(transactionId);
        if (txn != null) {
            this.scheduledTransactionManager.deleteTransaction(txn);
        }
    }

    @Override
    public ValidationResponse validateTransaction(ScheduledTransaction transaction) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ValidationResponse rebuildOriginals(ScheduledTransaction transaction) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ScheduledTransaction getById(String txnId) {
        ScheduledTransaction txn = this.scheduledTransactionManager.getById(txnId);
        return txn;
    }

    @Override
    public List<ScheduledTransaction> getScheduledTransactions(String accountId) {
        Account account = this.accountManager.getAccountById(accountId);
        return this.scheduledTransactionManager.getOriginalTransactions(account);
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactions(String accountId, String theTimePeriod) {
        Account account = this.accountManager.getAccountById(accountId);
        if (account != null) {
            return this.scheduledTransactionManager.getUpcomingTransactionsForTimePeriod(account, theTimePeriod);
        }
        return null;
    }

    @Override
    public void payTransaction(String transactionId) throws Exception {
        ScheduledTransaction txn = this.scheduledTransactionManager.getById(transactionId);
        if (txn != null && txn.getOriginalTransactionId() != null) {
            if (!ScheduledTransaction.STATUS_SKIPPED.equalsIgnoreCase(txn.getStatusTxt())) {
                txn.setStatusTxt(ScheduledTransaction.STATUS_PAID);
                this.scheduledTransactionManager.updateTransaction(txn);
            }
        }
    }

}
