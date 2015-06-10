/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.dao.ScheduledTransactionDAO;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.ScheduledTransaction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pldorrell
 */
public class ScheduledTransactionManagerImpl implements ScheduledTransactionManager {

    private static SimpleDateFormat transactionDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    ScheduledTransactionDAO scheduledTransactionDAO;

    public ScheduledTransactionDAO getScheduledTransactionDAO() {
        return scheduledTransactionDAO;
    }

    public void setScheduledTransactionDAO(ScheduledTransactionDAO scheduledTransactionDAO) {
        this.scheduledTransactionDAO = scheduledTransactionDAO;
    }

    @Override
    public ScheduledTransaction createTransaction(ScheduledTransaction txn) {

        

        //create the original transaction
        txn.setOriginal(true);
        ScheduledTransaction newOrigTransaction = this.scheduledTransactionDAO.createTransaction(txn);
        this.buildUpcomingScheduledTxns(newOrigTransaction);

        return newOrigTransaction;
    }

    private void buildUpcomingScheduledTxns(ScheduledTransaction txn) {
        //logic for setting up dates
        //if the txn has an endDate need to determine number of Occurrences        
        //if the txn has number of Occurrences need to determine end Date
        ArrayList<String> recurrenceDates = this.getRecurringDates(txn);
        
        //create the schedule
        int index = 1;
        for (String recurrenceDate: recurrenceDates) {
            ScheduledTransaction newSchedTxn = this.cloneTransaction(txn);
            newSchedTxn.setOccurenceNumber(index++);
            newSchedTxn.setId(null);
            newSchedTxn.setOriginalTransactionId(txn.getId());
            newSchedTxn.setScheduledDate(recurrenceDate);
            newSchedTxn.setOriginal(false);
            this.scheduledTransactionDAO.createTransaction(newSchedTxn);
        }        
    }
    public ArrayList<String> getRecurringDates(ScheduledTransaction txn) {
        ArrayList<String> recurringDates = new ArrayList<String>();
        if (txn.getEndDate() != null && !txn.getEndDate().equals("")) {
            Date endDate = null;
            Date beginDate = null;
            Date scheduledDate = null;
            try {
                //convert the string to a date
                endDate = transactionDateFormatter.parse(txn.getEndDate());
                beginDate = transactionDateFormatter.parse(txn.getBeginDate());
                Calendar c = Calendar.getInstance();
                //if the scheduledDate is empty(first schedule hasn't been determined yet) or the scheduled date
                //is less than the input end date
                while (scheduledDate == null || scheduledDate.compareTo(endDate) <= 0) {
                    if (scheduledDate == null) {
                        scheduledDate = beginDate;
                        recurringDates.add(transactionDateFormatter.format(scheduledDate));
                    } else {
                        /*
                         public final static int FREQUENCY_WEEKLY = 1;
                         public final static int FREQUENCY_MONTHLY = 2;
                         public final static int FREQUENCY_DAILY = 3;
                         public final static int FREQUENCY_YEARLY = 4;
                         public final static int FREQUENCY_CUSTOM = 5;
                         public final static int FREQUENCY_ONE_TIME = 0;
                         */

                        if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_ONE_TIME) {
                            //the scheduleDate added in the first iteration of the loop should be ok
                        } else if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_DAILY) {

                            c.setTime(scheduledDate);
                            c.add(Calendar.DATE, 1);
                            scheduledDate = c.getTime();
                            if (scheduledDate.compareTo(endDate) <= 0) {
                                recurringDates.add(transactionDateFormatter.format(scheduledDate));
                            }
                        } else if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_WEEKLY) {
                            c.setTime(scheduledDate);
                            c.add(Calendar.DATE, 7);
                            scheduledDate = c.getTime();
                            if (scheduledDate.compareTo(endDate) <= 0) {
                                recurringDates.add(transactionDateFormatter.format(scheduledDate));
                            }
                        } else if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_MONTHLY) {
                            c.setTime(scheduledDate);
                            c.add(Calendar.MONTH, 1);
                            scheduledDate = c.getTime();
                            if (scheduledDate.compareTo(endDate) <= 0) {
                                recurringDates.add(transactionDateFormatter.format(scheduledDate));
                            }
                        } else if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_YEARLY) {
                            c.setTime(scheduledDate);
                            c.add(Calendar.YEAR, 1);
                            scheduledDate = c.getTime();
                            if (scheduledDate.compareTo(endDate) <= 0) {
                                recurringDates.add(transactionDateFormatter.format(scheduledDate));
                            }
                        } else if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_CUSTOM) {
                            throw new UnsupportedOperationException("Custom frequency not implemented");
                        }
                    }

                }

            } catch (ParseException ex) {
                Logger.getLogger(ScheduledTransactionManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (txn.getNumberOfOccurrences() > 0) {
            Date endDate = null;
            {

            }
            Date beginDate = null;
            Date scheduledDate = null;
            try {
                //convert the string to a date
                beginDate = transactionDateFormatter.parse(txn.getBeginDate());
                Calendar c = Calendar.getInstance();
                if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_ONE_TIME) {
                    recurringDates.add(transactionDateFormatter.format(beginDate));
                } else if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_CUSTOM) {
                            throw new UnsupportedOperationException("Custom frequency not implemented");
                       
                } else {
                    scheduledDate = beginDate;
                    recurringDates.add(transactionDateFormatter.format(scheduledDate));
                    for (int i = 1; i < txn.getNumberOfOccurrences(); i++) {
                        if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_DAILY) {

                            c.setTime(scheduledDate);
                            c.add(Calendar.DATE, 1);
                            scheduledDate = c.getTime();
                            recurringDates.add(transactionDateFormatter.format(scheduledDate));

                        } else if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_WEEKLY) {
                            c.setTime(scheduledDate);
                            c.add(Calendar.DATE, 7);
                            scheduledDate = c.getTime();

                            recurringDates.add(transactionDateFormatter.format(scheduledDate));

                        } else if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_MONTHLY) {
                            c.setTime(scheduledDate);
                            c.add(Calendar.MONTH, 1);
                            scheduledDate = c.getTime();

                            recurringDates.add(transactionDateFormatter.format(scheduledDate));

                        } else if (txn.getFrequency() == ScheduledTransaction.FREQUENCY_YEARLY) {
                            c.setTime(scheduledDate);
                            c.add(Calendar.YEAR, 1);
                            scheduledDate = c.getTime();

                            recurringDates.add(transactionDateFormatter.format(scheduledDate));

                        }
                    }
                }

            } catch (ParseException ex) {
                Logger.getLogger(ScheduledTransactionManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return recurringDates;
    }

    protected ScheduledTransaction cloneTransaction(ScheduledTransaction txn) {
        ScheduledTransaction newSchedTxn = new ScheduledTransaction();
        newSchedTxn.setCategory(txn.getCategory());
        newSchedTxn.setPayee(txn.getPayee());
        newSchedTxn.setCategorySplits(txn.getCategorySplits());
        newSchedTxn.setTxnAmount(txn.getTxnAmount());
        newSchedTxn.setPrimaryAccount(txn.getPrimaryAccount());
        newSchedTxn.setCredit(txn.isCredit());
        newSchedTxn.setSecondaryAccount(txn.getSecondaryAccount());
        return newSchedTxn;
    }

    @Override
    public void deleteTransaction(ScheduledTransaction txn) {
        //if an original, delete scheduled and then delete original
        if (txn.getOriginalTransactionId() == null) {
            this.deleteChildTransactions(txn);
            this.scheduledTransactionDAO.deleteTransaction(txn);
        } else {
            this.scheduledTransactionDAO.deleteTransaction(txn);
        }
       
    }

    @Override
    public ScheduledTransaction updateTransaction(ScheduledTransaction txn) {
        return this.scheduledTransactionDAO.updateTransaction(txn);
    }

    @Override
    public List<ScheduledTransaction> getOriginalTransactions(Account account) {
        return this.scheduledTransactionDAO.getOriginalTransactionsByAccountId(account.getId());
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactions(Account account) {
        return this.scheduledTransactionDAO.getUpcomingTransactionsByAccountId(account.getId());
    }

    @Override
    public ScheduledTransaction getById(String id) {
        return this.scheduledTransactionDAO.getById(id);
    }

    @Override
    public List<ScheduledTransaction> getLatestUpcomingTransactions(Account account) {
        List<ScheduledTransaction> retrievedTxns = this.scheduledTransactionDAO.getUpcomingTransactionsByAccountId(account.getId());
        List<ScheduledTransaction> txns = new ArrayList<ScheduledTransaction>();
        String lastId = "";
        for (ScheduledTransaction txn: retrievedTxns) {
            if (!txn.getId().equals(lastId)){
                txns.add(txn);
            }
            lastId = txn.getId();
        }
        return txns;
    }

    @Override
    public ScheduledTransaction getLatestUpcomingTransaction(ScheduledTransaction txn) {
        List<ScheduledTransaction> retrievedTxns = this.getUpcomingTransactions(txn);
        if (retrievedTxns != null) {
            return retrievedTxns.get(0);
        }
        return null;
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactions(ScheduledTransaction txn) {
        return this.scheduledTransactionDAO.getUpcomingTransactionsByOriginalTxnId(txn.getId());
    }

    @Override
    public void rebuildUpcomingTransactions(ScheduledTransaction _txn) {
        //delete all upcoming transactions for the scheduled txns
        this.deleteChildTransactions(_txn);
        
        //rebuild all
        this.buildUpcomingScheduledTxns(_txn);

    }
    private void deleteChildTransactions(ScheduledTransaction _txn) {

        List<ScheduledTransaction> txns = this.getUpcomingTransactions(_txn);
        for (ScheduledTransaction txn: txns ){
            this.deleteTransaction(txn);
        }        
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsForTimePeriod(Account account, String timePeriod) {
        return this.scheduledTransactionDAO.getUpcomingTransactionsByAccountIdForTimePeriod(account.getId(), timePeriod);
    }

}
