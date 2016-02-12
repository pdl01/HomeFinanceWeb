/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.ScheduledTransaction;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface ScheduledTransactionManager {
    public static SimpleDateFormat transactionDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public ScheduledTransaction createTransaction(ScheduledTransaction txn);
    public void deleteTransaction(ScheduledTransaction txn);
    public ScheduledTransaction updateTransaction(ScheduledTransaction txn);
    public ScheduledTransaction getById(String id);
    public void rebuildUpcomingTransactions(ScheduledTransaction txn);
    
    public List<ScheduledTransaction> getOriginalTransactions(Account account);
    public List<ScheduledTransaction> getUpcomingTransactions(Account account);
    public List<ScheduledTransaction> getUpcomingTransactionsForTimePeriod(Account account,String timePeriod);
    public List<ScheduledTransaction> getLatestUpcomingTransactions(Account account);
    public ScheduledTransaction getLatestUpcomingTransaction(ScheduledTransaction txn);
    public List<ScheduledTransaction> getUpcomingTransactions(ScheduledTransaction txn);
}
