/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hf.hfw.dao;

import com.hf.homefinanceshared.ScheduledTransaction;
import java.util.List;

/**
 *
 * @author phillip.dorrell
 */
public interface ScheduledTransactionDAO {
    public ScheduledTransaction getById(String id);
    public ScheduledTransaction createTransaction(ScheduledTransaction txn);
    public void deleteTransaction(ScheduledTransaction txn);
    public ScheduledTransaction updateTransaction(ScheduledTransaction txn);
    
    
    public List<ScheduledTransaction> getOriginalTransactionsByAccountId(String id);
    public List<ScheduledTransaction> getUpcomingTransactionsByAccountId(String id);
    public List<ScheduledTransaction> getUpcomingTransactionsByAccountIdForTimePeriod(String id,String timePeriod);
    public List<ScheduledTransaction> getLatestUpcomingTransactionsByAccountId(String id);
    public ScheduledTransaction getLatestUpcomingTransactionsByOriginalTxnId(String id);
    public List<ScheduledTransaction> getUpcomingTransactionsByOriginalTxnId(String id);

}
