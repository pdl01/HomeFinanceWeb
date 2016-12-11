
package com.hf.hfw.dao.h2;

import com.hf.hfw.dao.ScheduledTransactionDAO;
import com.hf.homefinanceshared.ScheduledTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pldor
 */
public class ScheduledTransactionDAOImpl extends H2DAO implements ScheduledTransactionDAO{

    @Override
    public ScheduledTransaction getById(String id) {
return null;
    }

    @Override
    public ScheduledTransaction createTransaction(ScheduledTransaction txn) {
return null;
    }

    @Override
    public void deleteTransaction(ScheduledTransaction txn) {

    }

    @Override
    public ScheduledTransaction updateTransaction(ScheduledTransaction txn) {
return null;
    }

    @Override
    public List<ScheduledTransaction> getOriginalTransactionsByAccountId(String id) {
        return new ArrayList<ScheduledTransaction>();
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsByAccountId(String id) {
        return new ArrayList<ScheduledTransaction>();
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsByAccountIdForTimePeriod(String id, String timePeriod) {
        return new ArrayList<ScheduledTransaction>();
    }

    @Override
    public List<ScheduledTransaction> getLatestUpcomingTransactionsByAccountId(String id) {
        return new ArrayList<ScheduledTransaction>();
    }

    @Override
    public ScheduledTransaction getLatestUpcomingTransactionsByOriginalTxnId(String id) {
return null;
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsByOriginalTxnId(String id) {
        return new ArrayList<ScheduledTransaction>();
    }
    
}
