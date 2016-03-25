package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.hfw.manager.RegisterManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.CategorySplit;
import com.hf.homefinanceshared.OnlineTransaction;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Path;
import org.apache.log4j.Logger;

/**
 *
 * @author phillip.dorrell
 */
@Path("/register")
public class RegisterServiceImpl implements RegisterService {
    private static final Logger log = Logger.getLogger(RegisterServiceImpl.class);

    protected RegisterManager registerManager;

    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }

    @Override
    public List<RegisterTransaction> getTransactions(String accountId) {
        Account account = new Account();
        account.setId(accountId);
        return this.registerManager.getTransactions(account);

    }

    @Override
    public List<RegisterTransaction> getTransactions(String accountId, String _number, String _start) {
        Account account = new Account();
        account.setId(accountId);
        int start = Integer.parseInt(_start);
        int number = Integer.parseInt(_number);
        List<RegisterTransaction> txns = this.registerManager.getTransactions(account);

        if (txns != null && txns.size() > number) {
            int end = start + number;
            if (start + number > txns.size()) {
                end = txns.size();
            }
            return txns.subList(start, end);

        }
        return txns;
        //return this.registerManager.getTransactions(account);

    }

    @Override
    public RegisterTransaction getTransactionById(String transactionId) {
        RegisterTransaction txn = this.registerManager.getTransactionById(transactionId);
        return txn;
    }

    @Override
    public RegisterTransaction saveTransaction(RegisterTransaction transaction) throws Exception {
        if (transaction.getId() != null) {
            RegisterTransaction retrievedtxn = this.registerManager.getTransactionById(transaction.getId());

            //transaction.setCreatedDate(retrievedtxn.getCreatedDate());
            //transaction.setLastModifiedDate(retrievedtxn.getLastModifiedDate());
            retrievedtxn.setTxnDate(transaction.getTxnDate());
            retrievedtxn.setPayee(transaction.getPayee());
            retrievedtxn.setCategorySplits(transaction.getCategorySplits());
            retrievedtxn.setTxnAmount(transaction.getTxnAmount());
            retrievedtxn.setPrimaryAccount(transaction.getPrimaryAccount());
            retrievedtxn.setCredit(transaction.isCredit());
            retrievedtxn.setStatusTxt(transaction.getStatusTxt());
            retrievedtxn.setTxnPersonalRefNumber(transaction.getTxnPersonalRefNumber());
            return this.registerManager.updateTransaction(retrievedtxn);
        } else {
            return this.registerManager.createTransaction(transaction);
        }
    }

    @Override
    public void deleteTransaction(String transactionId) {
        RegisterTransaction txn = this.registerManager.getTransactionById(transactionId);
        if (txn != null) {
            this.registerManager.deleteTransaction(txn);
        }
    }

    @Override
    public ValidationResponse validateTransaction(RegisterTransaction transaction) throws Exception {
        //do some validation
        ValidationResponse response = new ValidationResponse();
        ArrayList<String> validationMessages = new ArrayList<String>();
        response.setValid(true);
        double categorySplitSum = 0.0;
        for (CategorySplit split : transaction.getCategorySplits()) {
            categorySplitSum += split.getTxnAmount();
        }
        if (categorySplitSum != transaction.getTxnAmount()) {
            response.setValid(false);
            validationMessages.add("Split totals does not equal transaction amounts.");

        }
        response.setMessages(validationMessages);
        return response;
    }

    @Override
    public void uploadFile(String accountId, String data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<OnlineTransaction> getPendingTransactions(String accountId) {
        Account account = new Account();
        account.setId(accountId);
        return this.registerManager.getPendingTransactions(account);
    }

    @Override
    public List<OnlineTransaction> getPendingTransactions(String accountId, String _number, String _start) {
        Account account = new Account();
        account.setId(accountId);
        int start = Integer.parseInt(_start);
        int number = Integer.parseInt(_number);
        List<OnlineTransaction> txns = this.registerManager.getPendingTransactions(account);

        if (txns != null && txns.size() > number) {
            int end = start + number;
            if (start + number > txns.size()) {
                end = txns.size();
            }
            return txns.subList(start, end);

        }
        return txns;
    }

    @Override
    public List<RegisterTransaction> getTransactionsByMonth(String accountId, String month) {
        Account account = new Account();
        account.setId(accountId);
        return this.registerManager.getAllTransactionsForDateStartWith(account, month);

    }

    @Override
    public List<RegisterTransaction> getTransactionsByDate(String accountId, String date) {
        Account account = new Account();
        account.setId(accountId);
        return this.registerManager.getAllTransactionsForDateStartWith(account, date);
    }

    @Override
    public List<RegisterTransaction> getMatchedTransactionsForPending(String transactionid) {
        //get the pending transaction with transactionid
        OnlineTransaction pendingTransaction = this.registerManager.getPendingTransactionById(transactionid);

        List<RegisterTransaction> txns = this.registerManager.matchTransaction(pendingTransaction);
        return txns;
    }

    @Override
    public ValidationResponse matchTransaction(String pendingTransactionid, String enteredTransactionId) {
        ValidationResponse response = new ValidationResponse();
        OnlineTransaction pendingTransaction = this.registerManager.getPendingTransactionById(pendingTransactionid);
        RegisterTransaction enteredTransaction = this.registerManager.getTransactionById(enteredTransactionId);
        ValidationResponse validationResponse = this.validateTransactionsCanBeMatched(pendingTransaction, enteredTransaction);
        if (!validationResponse.isValid()) {
            return validationResponse;
        }
        this.registerManager.matchTransaction(pendingTransaction, enteredTransaction);
        response.setValid(true);
        return response;
    }

    private ValidationResponse validateTransactionsCanBeMatched(RegisterTransaction pendingTransaction, RegisterTransaction enteredTransaction) {
        ValidationResponse response = new ValidationResponse();
        response.setValid(true);
        List<String> messages = new ArrayList<String>();
        if (pendingTransaction == null) {
            response.setValid(false);
            messages.add("Pending Transaction is not valid");
        } else if (enteredTransaction == null) {
            response.setValid(false);
            messages.add("Existing Transaction Id is not valid");

        } else if (!pendingTransaction.getStatusTxt().equals(RegisterTransaction.STATUS_IMPORTED)) {
            response.setValid(false);
            messages.add("Pending Transaction not in correct status");
        } else if (!enteredTransaction.getStatusTxt().equals(RegisterTransaction.STATUS_NONE)) {
            response.setValid(false);
            messages.add("Entered Transaction not in correct status");

        } else if (pendingTransaction.getPrimaryAccount() == null || enteredTransaction.getPrimaryAccount() == null || !pendingTransaction.getPrimaryAccount().equals(enteredTransaction.getPrimaryAccount())) {
            response.setValid(false);
            messages.add("Accounts associated with transaction do not match");

        } else if (pendingTransaction.getTxnAmount() != enteredTransaction.getTxnAmount()) {
            response.setValid(false);
            messages.add("Amounts associated with transaction do not match");
        }
        response.setMessages(messages);
        return response;
    }

    @Override
    public ValidationResponse dismissPendingTransaction(String pendingTransactionid) {
        OnlineTransaction pendingTransaction = this.registerManager.getPendingTransactionById(pendingTransactionid);
        ValidationResponse response = new ValidationResponse();
        List<String> messages = new ArrayList<String>();
        response.setValid(true);
        if (pendingTransaction == null) {
            response.setValid(false);
            messages.add("Transaction Id is not valid");
        }

        if (response.isValid()) {
            pendingTransaction.setStatusTxt(RegisterTransaction.STATUS_DISMISSED);
            this.registerManager.addPendingTransactions(pendingTransaction);
            return response;
        } else {
            response.setMessages(messages);
            return response;
        }
    }

    @Override
    public RegisterTransaction acceptPendingTransactionAsNew(String pendingTransactionid) {
        OnlineTransaction pendingTransaction = this.registerManager.getPendingTransactionById(pendingTransactionid);

        try {
            //clone the transaction
            RegisterTransaction newTransaction = this.copyOnlineTransactionToRegisterTransaction(pendingTransaction);//RegisterTransaction)pendingTransaction.clone();
            
            //save the transaction
            newTransaction.setId(null);
            newTransaction.setStatusTxt(RegisterTransaction.STATUS_CLEARED);
            newTransaction = this.registerManager.createTransaction(newTransaction);
            
            //save the pending as accepted
            pendingTransaction.setStatusTxt(RegisterTransaction.STATUS_ACCEPTED);
            this.registerManager.addPendingTransactions(pendingTransaction);
            return newTransaction;
        
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    private RegisterTransaction copyOnlineTransactionToRegisterTransaction(OnlineTransaction txn) {
        RegisterTransaction transaction = new RegisterTransaction();
        transaction.setPayee(txn.getPayee());
        transaction.setTxnAmount(txn.getTxnAmount());
        transaction.setTxnDate(txn.getTxnDate());
        transaction.setCategorySplits(txn.getCategorySplits());
        transaction.setMemo(txn.getMemo());
        transaction.setTxnExternalRefNumber(txn.getTxnExternalRefNumber());
        transaction.setPrimaryAccount(txn.getPrimaryAccount());
        transaction.setCredit(txn.isCredit());
        transaction.setTxnPersonalRefNumber(txn.getTxnPersonalRefNumber());
        transaction.setSecondaryAccount(txn.getSecondaryAccount());
        return transaction;
    }

    @Override
    public void acceptAllPendingTransactionAsNew(String accountId) {
        List<OnlineTransaction> pendingTransactions = this.getPendingTransactions(accountId);
        for (OnlineTransaction pendingTransaction:pendingTransactions) {
            this.acceptPendingTransactionAsNew(pendingTransaction.getId());
        }
    }
}

