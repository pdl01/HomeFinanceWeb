/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.mongo;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.hfw.dao.RegisterDAO;
import com.hf.homefinanceshared.CategorySplit;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author phillip.dorrell
 */
public class RegisterDAOImpl extends AbstractMongoDAO implements RegisterDAO {
    private static final Logger log = Logger.getLogger(RegisterDAOImpl.class);

    @Override
    public List<RegisterTransaction> getTransactions(Account account) {
        Query searchTransactionsQuery = new Query(Criteria.where("primaryAccount").is(account.getId()));
        searchTransactionsQuery.with(new Sort(Sort.Direction.DESC,"txnDate"));
        
        return this.getMongoTemplate().find(searchTransactionsQuery, RegisterTransaction.class);
    
    }

    @Override
    public RegisterTransaction getTransactionById(String _id) {
        Query searchTransactionQuery = new Query(Criteria.where("id").is(_id));
        return this.getMongoTemplate().findOne(searchTransactionQuery, RegisterTransaction.class);
    }

    @Override
    public RegisterTransaction createTransaction(RegisterTransaction txn) {
        this.getMongoTemplate().save(txn);
        return txn;
    }

    @Override
    public void deleteTransaction(RegisterTransaction txn) {
        this.getMongoTemplate().remove(txn);
    }

    @Override
    public RegisterTransaction updateTransaction(RegisterTransaction txn) {
        this.getMongoTemplate().save(txn);
        return txn;
    }

    @Override
    public List<RegisterTransaction> getTransactionsByCategories(Account account, List<String> categories) {
        Query searchTransactionsQuery = new Query(Criteria.where("primaryAccount").is(account.getId()));
        searchTransactionsQuery.with(new Sort(Sort.Direction.ASC,"txnDate"));

        searchTransactionsQuery.addCriteria(Criteria.where("categorySplits.category").in(categories));
        return this.getMongoTemplate().find(searchTransactionsQuery, RegisterTransaction.class);
    }

    @Override
    public List<RegisterTransaction> getTransactionsByCategoriesStartsWithForDateStartWith(Account account, String category, String date) {
        Query searchTransactionsQuery = new Query(Criteria.where("primaryAccount").is(account.getId()));
        searchTransactionsQuery.with(new Sort(Sort.Direction.ASC,"txnDate"));

        searchTransactionsQuery.addCriteria(Criteria.where("categorySplits.category").regex("^" + category));
        if (date != null) {
            searchTransactionsQuery.addCriteria(Criteria.where("txnDate").regex("^" + date));
        }
        //searchTransactionsQuery.addCriteria(Criteria.where("{\"categorySplits.category\": {$regex : '^" + category + "'} }"));
        //"{\"categorySplits.category\": {$regex : '^" + category + "'} }"
        return this.getMongoTemplate().find(searchTransactionsQuery, RegisterTransaction.class);
    }

    
    
    @Override
    public Set<String> getAllCategories() {
        List<RegisterTransaction> txns = this.getMongoTemplate().findAll(RegisterTransaction.class);
        TreeSet<String> categories = new TreeSet<String>();
        for (RegisterTransaction txn : txns) {
            for (CategorySplit split : txn.getCategorySplits()) {
                categories.add(split.getCategory());
                log.info("Adding "+split.getCategory());
            }
        }
        return categories;
    }

    @Override
    public List<RegisterTransaction> getTransactionsForDateStartWith(Account account, String date, boolean getCredit) {
        Query searchTransactionsQuery = new Query(Criteria.where("primaryAccount").is(account.getId()));
        searchTransactionsQuery.with(new Sort(Sort.Direction.ASC,"txnDate"));

        if (getCredit) {
            searchTransactionsQuery.addCriteria(Criteria.where("credit").is(true));
        } else {
           searchTransactionsQuery.addCriteria(Criteria.where("credit").is(false)); 
        }
        if (date != null) {
            searchTransactionsQuery.addCriteria(Criteria.where("txnDate").regex("^" + date));
        }
        //searchTransactionsQuery.addCriteria(Criteria.where("{\"categorySplits.category\": {$regex : '^" + category + "'} }"));
        //"{\"categorySplits.category\": {$regex : '^" + category + "'} }"
        return this.getMongoTemplate().find(searchTransactionsQuery, RegisterTransaction.class);    
    }

    @Override
    public List<RegisterTransaction> getAllTransactionsForDateStartWith(Account account, String date) {
        Query searchTransactionsQuery = new Query(Criteria.where("primaryAccount").is(account.getId()));
        searchTransactionsQuery.with(new Sort(Sort.Direction.ASC,"txnDate"));

        
        if (date != null) {
            searchTransactionsQuery.addCriteria(Criteria.where("txnDate").regex("^" + date));
        }
        //searchTransactionsQuery.addCriteria(Criteria.where("{\"categorySplits.category\": {$regex : '^" + category + "'} }"));
        //"{\"categorySplits.category\": {$regex : '^" + category + "'} }"
        return this.getMongoTemplate().find(searchTransactionsQuery, RegisterTransaction.class);    
    }

    
    @Override
    public void addPendingTransactions( List<RegisterTransaction> txns) {
        for (RegisterTransaction txn: txns) {
            this.getMongoTemplate().save(txn, "onlineData");    
        }
        
    }

    @Override
    public List<RegisterTransaction> getPendingTransactions(Account account) {
        Query searchTransactionsQuery = new Query(Criteria.where("primaryAccount").is(account.getId()));
        searchTransactionsQuery.with(new Sort(Sort.Direction.ASC,"txnDate"));

        return this.getMongoTemplate().find(searchTransactionsQuery, RegisterTransaction.class, "onlineData");
    }

    @Override
    public RegisterTransaction getPendingTransactionById(String _id) {
        Query searchTransactionQuery = new Query(Criteria.where("id").is(_id));
        return this.getMongoTemplate().findOne(searchTransactionQuery, RegisterTransaction.class,"onlineData");
    }

    @Override
    public List<RegisterTransaction> matchTransaction(RegisterTransaction pendingTransaction) {
        Query searchTransactionQuery = new Query(Criteria.where("txnAmount").is(pendingTransaction.getTxnAmount()));
        searchTransactionQuery.addCriteria(Criteria.where("statusTxt").is(RegisterTransaction.STATUS_NONE));
        searchTransactionQuery.addCriteria(Criteria.where("primaryAccount").is(pendingTransaction.getPrimaryAccount()));
        return this.getMongoTemplate().find(searchTransactionQuery, RegisterTransaction.class);
    }

    
}
