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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author phillip.dorrell
 */
public class RegisterManagerImpl implements RegisterManager {

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

    @Override
    public RegisterTransaction createTransaction(RegisterTransaction txn) {
        return this.saveTransaction(txn);
    }

    @Override
    public void deleteTransaction(RegisterTransaction txn) {
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
        if (_txn.getId() == null) {
            _txn.setCreatedDate(new Date());
            if (_txn.getTxnDate() == null) {
                _txn.setTxnDate(transactionDateFormatter.format(new Date()));
            }
            RegisterTransaction txn = this.registerDAO.createTransaction(_txn);
            this.fireTransactionEvent(txn, TransactionEvent.TransactionEventType.ADDED);
            return txn;
        } else {
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

}
