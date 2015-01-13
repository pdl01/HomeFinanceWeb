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
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author phillip.dorrell
 */
public class RegisterDAOImpl extends AbstractMongoDAO implements RegisterDAO {

    @Override
    public List<RegisterTransaction> getTransactions(Account account) {
        Query searchTransactionsQuery = new Query(Criteria.where("primaryAccount").is(account.getId()));
        searchTransactionsQuery.with(new Sort(Sort.Direction.ASC,"txnDate"));
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
        return this.getMongoTemplate().find(searchTransactionsQuery, RegisterTransaction.class);    }

}
