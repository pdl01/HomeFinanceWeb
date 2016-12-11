/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.accounts.listeners;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.RegisterManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
import java.util.Date;
import java.util.List;

/**
 *
 * @author phillip.dorrell
 */
public abstract class BalanceListener {

    protected AccountManager accountManager;
    protected RegisterManager registerManager;

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }

    protected void rebalanceAccount(Account _account) {
        List<RegisterTransaction> txns = null;
        
        try {
            txns = this.registerManager.getTransactions(_account);
        }catch (Exception e) {
           
        }
        if (txns == null) {
            return;
        }
        double accountBalance = _account.getStartingBalance();
        for (RegisterTransaction txn : txns) {
            if (!txn.isVoid()) {
                if (txn.isCredit()) {
                    accountBalance += txn.getTxnAmount();
                } else {
                    accountBalance -= txn.getTxnAmount();
                }
            }

        }

        Account account = this.accountManager.getAccountById(_account.getId());
        account.setBalanceLastCalculatedDate(new Date());
        account.setCurrentBalance(accountBalance);
        this.accountManager.updateAccount(account, AccountEvent.AccountEventType.UPDATED_BALANCE);

    }
}
