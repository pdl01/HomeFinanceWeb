/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.dao.DailyBalanceDAO;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.DailyBalance;
import com.hf.homefinanceshared.RegisterTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public class DailyBalanceManagerImpl implements DailyBalanceManager {

    private DailyBalanceDAO dailyBalanceDAO;
    private AccountManager accountManager;
    private RegisterManager registerManager;

    @Override
    public List<DailyBalance> getByAccountId(String accountId) {
        return this.dailyBalanceDAO.getByAccountId(accountId);
    }

    @Override
    public List<DailyBalance> getByAccountIdForDate(String accountId, String date) {
        return this.dailyBalanceDAO.getByAccountIdForDate(accountId, date);
    }

    @Override
    public void updateAccount(DailyBalance db) {
        this.dailyBalanceDAO.updateAccount(db);
    }

    @Override
    public void deleteAllForAccount(String accountId) {
        this.dailyBalanceDAO.deleteAllForAccount(accountId);
    }

    @Override
    public void buildForAccount(String accountId) {
        //get the day the account was created
        Account account = this.accountManager.getAccountById(accountId);

        //get all transactions for the account sorted by date
        List<RegisterTransaction> txns = this.registerManager.getTransactions(account);
        double accountBalance = account.getStartingBalance();
        String currentDate = null;
        //sort the list

        List<DailyBalance> dbs = new ArrayList<DailyBalance>();
        //go through the list 
        for (RegisterTransaction txn : txns) {
            if (currentDate == null) {
                currentDate = txn.getTxnDate();
            }
            if (!currentDate.equals(txn.getTxnDate())) {
                //save the amount for the currentDate
                DailyBalance db = new DailyBalance();
                db.setAccountId(accountId);
                db.setAmount(accountBalance);
                db.setDailyBalanceDate(currentDate);
                dbs.add(db);
                //set up the currentDate 
                currentDate = txn.getTxnDate();

            }
            if (txn.isCredit()) {
                accountBalance = accountBalance + txn.getTxnAmount();
            } else {
                accountBalance = accountBalance - txn.getTxnAmount();
            }

        }
        //fill in the missing dates

    }

}
