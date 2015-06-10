/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.mongo;

import com.hf.hfw.dao.ScheduledTransactionDAO;
import com.hf.homefinanceshared.ScheduledTransaction;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author pldorrell
 */
public class ScheduleTransactionDAOImpl extends AbstractMongoDAO implements ScheduledTransactionDAO {

    @Override
    public ScheduledTransaction createTransaction(ScheduledTransaction txn) {        
        this.getMongoTemplate().save(txn,"ScheduledTransactions");
        return txn;

    }

    @Override
    public void deleteTransaction(ScheduledTransaction txn) {
        this.getMongoTemplate().remove(txn,"ScheduledTransactions");
    }

    @Override
    public ScheduledTransaction updateTransaction(ScheduledTransaction txn) {
        this.getMongoTemplate().save(txn,"ScheduledTransactions");
        return txn;

    }

    @Override
    public List<ScheduledTransaction> getOriginalTransactionsByAccountId(String id) {
        Query searchTxnQuery = new Query(Criteria.where("primaryAccount").is(id));
        searchTxnQuery.addCriteria(Criteria.where("original").is(true));
        searchTxnQuery.with(new Sort(Sort.Direction.ASC,"scheduledDate"));
        
        return this.getMongoTemplate().find(searchTxnQuery, ScheduledTransaction.class,"ScheduledTransactions");
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsByAccountId(String id) {
        Query searchTxnQuery = new Query(Criteria.where("primaryAccount").is(id));
        searchTxnQuery.addCriteria(Criteria.where("original").is(false));
        searchTxnQuery.with(new Sort(Sort.Direction.ASC,"scheduledDate"));
        
        return this.getMongoTemplate().find(searchTxnQuery, ScheduledTransaction.class,"ScheduledTransactions");

    }

    @Override
    public ScheduledTransaction getById(String id) {
        Query searchTxnQuery = new Query(Criteria.where("id").is(id));
        return this.getMongoTemplate().findOne(searchTxnQuery, ScheduledTransaction.class,"ScheduledTransactions");
    }

    

    @Override
    public List<ScheduledTransaction> getLatestUpcomingTransactionsByAccountId(String id) {
       
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ScheduledTransaction getLatestUpcomingTransactionsByOriginalTxnId(String id) {
        //TODO: this belongs in the mananger class since no real DAO concern
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsByOriginalTxnId(String id) {
        Query searchTxnQuery = new Query(Criteria.where("originalTransactionId").is(id));
        return this.getMongoTemplate().find(searchTxnQuery, ScheduledTransaction.class,"ScheduledTransactions");
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsByAccountIdForTimePeriod(String id, String timePeriod) {
        Query searchTxnQuery = new Query(Criteria.where("primaryAccount").is(id));
        searchTxnQuery.addCriteria(Criteria.where("original").is(false));
        searchTxnQuery.addCriteria(Criteria.where("scheduledDate").regex("^" + timePeriod));
        searchTxnQuery.with(new Sort(Sort.Direction.ASC,"scheduledDate"));
        
        return this.getMongoTemplate().find(searchTxnQuery, ScheduledTransaction.class,"ScheduledTransactions");

    }

}
