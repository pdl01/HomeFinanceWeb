/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.hfw.accounts.events.TransactionEvent;
import com.hf.hfw.application.ApplicationState;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.hfw.dao.RegisterDAO;
import com.hf.homefinanceshared.CategorySplit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author phillip.dorrell
 */
public class RegisterManagerImpl implements RegisterManager {
private static final Logger log = Logger.getLogger(RegisterManagerImpl.class);
    private RegisterDAO registerDAO;
private static SimpleDateFormat transactionDateFormatter = new SimpleDateFormat("YYYY-MM-d");
    public RegisterDAO getRegisterDAO() {
        return registerDAO;
    }

    public void setRegisterDAO(RegisterDAO registerDAO) {
        this.registerDAO = registerDAO;
    }

    @Override
    public List<RegisterTransaction> getTransactions(Account account) {
        return this.registerDAO.getTransactions(account);

    }

    @Override
    public RegisterTransaction getTransactionById(String _id) {
        return this.registerDAO.getTransactionById(_id);

    }
    
    public List<RegisterTransaction>  getSupportingTransactions(RegisterTransaction txn) {
       return null; 
    }
    
    public List<RegisterTransaction>  createSupportingTransactions(RegisterTransaction txn) {
       return null; 
    }

    public boolean  validateSupportingTransactions(RegisterTransaction txn) {
        //mark the transfers to create transactions in those accounts
        if (txn.getCategorySplits() != null) {
            for (CategorySplit categorySplit: txn.getCategorySplits()) {
                if (categorySplit.getCategory().startsWith(CategoryCacheManagerImpl.TRANSFER_FROM)) {
                    //determine the account being accessed
                    String accountName = categorySplit.getCategory().substring(CategoryCacheManagerImpl.TRANSFER_FROM.length());
                    log.debug(accountName);
                    
                    //record a debit transaction in that account
                    //set the transfer transaction on the category split
                    
                } else if (categorySplit.getCategory().startsWith(CategoryCacheManagerImpl.TRANSFER_TO)) {
                    //determine the account being accessed
                    String accountName = categorySplit.getCategory().substring(CategoryCacheManagerImpl.TRANSFER_FROM.length());
                    log.debug(accountName);
                    
                    
                    //record a credit transaction in that account
                    //set the transfer transaction on the account
                    //set the transfer transaction on the category split
                }
                    
            }
        }
        return false;
    }
    
    
    
    
    @Override
    public RegisterTransaction createTransaction(RegisterTransaction txn) {

        RegisterTransaction rtnTxn =  this.saveTransaction(txn);
        if (rtnTxn.getId() != null) {
            //perform actions on the other transactions
        }
        return rtnTxn;
        
    }

    
    
    @Override
    public void deleteTransaction(RegisterTransaction txn) {
        //mark as void the supporting txn for the category splits
        this.registerDAO.deleteTransaction(txn);
    }

    @Override
    public RegisterTransaction updateTransaction(RegisterTransaction txn) {
        return this.saveTransaction(txn);
    }
    
    /*
    * This is a change new change
    */
    protected RegisterTransaction saveTransaction(RegisterTransaction _txn) {
        _txn.setLastModifiedDate(new Date());
        //created
        if (_txn.getId() == null) {
            _txn.setCreatedDate(new Date());
            if (_txn.getTxnDate() == null || "".equals(_txn.getTxnDate())) {
                _txn.setTxnDate(transactionDateFormatter.format(new Date()));
            }
            if (_txn.getStatusTxt() == null || "".equals(_txn.getStatusTxt())) {
                _txn.setStatusTxt("p");
            }
            RegisterTransaction txn = this.registerDAO.createTransaction(_txn);
            this.fireTransactionEvent(txn, TransactionEvent.TransactionEventType.ADDED);
            return txn;
        //update
        } else {
            if (_txn.getTxnDate() == null || "".equals(_txn.getTxnDate())) {
                _txn.setTxnDate(transactionDateFormatter.format(new Date()));
            }
            RegisterTransaction txn = this.registerDAO.updateTransaction(_txn);
            this.fireTransactionEvent(txn, TransactionEvent.TransactionEventType.MODIFIED);
            return txn;
        }
    }

    protected void fireTransactionEvent(RegisterTransaction _txn, TransactionEvent.TransactionEventType _type) {
        ApplicationContext appContext = ApplicationState.getApplicationState().getCtx();
        TransactionEvent event = new TransactionEvent(appContext, _txn, _type);
        appContext.publishEvent(event);

    }

    @Override
    public List<RegisterTransaction> getTransactionsByCategories(Account account, List<String> categories) {
        return this.registerDAO.getTransactionsByCategories(account, categories);
    }
    
    @Override
    public List<RegisterTransaction> getTransactionsByCategoriesStartsWithForDateStartWith(Account account, String category,String date){
        return this.registerDAO.getTransactionsByCategoriesStartsWithForDateStartWith(account, category,date);

    }

    @Override
    public List<RegisterTransaction> getTransactionsForDateStartWith(Account account, String date, boolean getCredit) {
        return this.registerDAO.getTransactionsForDateStartWith(account, date, getCredit);
        }

    @Override
    public void addPendingTransactions(List<RegisterTransaction> txns) {
        this.registerDAO.addPendingTransactions(txns);
    }

    @Override
    public void addPendingTransactions(RegisterTransaction txn) {
        ArrayList<RegisterTransaction> txns = new ArrayList<RegisterTransaction>();
        txns.add(txn);
        this.addPendingTransactions(txns);
    }

    @Override
    public List<RegisterTransaction> getPendingTransactions(Account account) {
        return this.registerDAO.getPendingTransactions(account);
    }

    @Override
    public List<RegisterTransaction> getTransactions(Account account, int start, int number) {
        return null;
    }

    @Override
    public List<RegisterTransaction> getAllTransactionsForDateStartWith(Account account, String date) {
        return this.registerDAO.getAllTransactionsForDateStartWith(account, date);
   }

    @Override
    public RegisterTransaction getPendingTransactionById(String id) {
        return this.registerDAO.getPendingTransactionById(id);
    }

    @Override
    public List<RegisterTransaction> matchTransaction(RegisterTransaction pendingTransaction) {
        return this.registerDAO.matchTransaction(pendingTransaction);
    }

    @Override
    public void matchTransaction(RegisterTransaction pendingTransaction, RegisterTransaction enteredTransaction) {
        pendingTransaction.setStatusTxt(RegisterTransaction.STATUS_ACCEPTED);
        this.addPendingTransactions(pendingTransaction);
        
        enteredTransaction.setStatusTxt(RegisterTransaction.STATUS_CLEARED);
        this.saveTransaction(enteredTransaction);
    }
            

}
