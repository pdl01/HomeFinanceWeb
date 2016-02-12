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
import com.hf.homefinanceshared.ScheduledTransaction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author pldorrell
 */
public class DailyBalanceManagerImpl implements DailyBalanceManager {

    private DailyBalanceDAO dailyBalanceDAO;
    private AccountManager accountManager;
    private RegisterManager registerManager;
    private ScheduledTransactionManager scheduledTransactionManager;
    private DailyBalanceDateHelper dailyBalanceDateHelper;
    
    public DailyBalanceDAO getDailyBalanceDAO() {
        return dailyBalanceDAO;
    }

    public void setDailyBalanceDAO(DailyBalanceDAO dailyBalanceDAO) {
        this.dailyBalanceDAO = dailyBalanceDAO;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }

    public ScheduledTransactionManager getScheduledTransactionManager() {
        return scheduledTransactionManager;
    }

    public void setScheduledTransactionManager(ScheduledTransactionManager scheduledTransactionManager) {
        this.scheduledTransactionManager = scheduledTransactionManager;
    }
    
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
        Date createdDate = account.getCreatedDate();
        
        //build a list of dates that need to be generated;
        //every date for the past;
        Set<Date> datesToBuild = this.dailyBalanceDateHelper.getDatesToBuildFor(createdDate, new Date());
        
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
        //add more for the scheduled transactions
        //List<ScheduledTransaction> upcomingTransactions = this.scheduledTransactionManager.getUpcomingTransactions(account);

        //up to 1 year from the current date
        //then 1 entry for each month past that up to 10 years.
    }
 
}
