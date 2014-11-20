/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.mongo;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.hfw.dao.RegisterDAO;
import java.util.List;
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
        return this.mongoTemplate.find(searchTransactionsQuery, RegisterTransaction.class);
    }

    @Override
    public RegisterTransaction getTransactionById(String _id) {
        Query searchTransactionQuery = new Query(Criteria.where("id").is(_id));
        return this.mongoTemplate.findOne(searchTransactionQuery, RegisterTransaction.class);
    }

    @Override
    public RegisterTransaction createTransaction(RegisterTransaction txn) {
        this.mongoTemplate.save(txn);
        return txn;
    }

    @Override
    public void deleteTransaction(RegisterTransaction txn) {
        this.mongoTemplate.remove(txn);
    }

    @Override
    public RegisterTransaction updateTransaction(RegisterTransaction txn) {
        this.mongoTemplate.save(txn);
        return txn;
    }

}
