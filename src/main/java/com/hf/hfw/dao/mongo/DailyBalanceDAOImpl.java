/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.mongo;

import com.hf.hfw.dao.DailyBalanceDAO;
import com.hf.homefinanceshared.DailyBalance;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author pldorrell
 */
public class DailyBalanceDAOImpl extends AbstractMongoDAO implements DailyBalanceDAO {

    @Override
    public List<DailyBalance> getByAccountId(String accountId) {
        Query searchTransactionsQuery = new Query(Criteria.where("accountId").is(accountId));
        searchTransactionsQuery.with(new Sort(Sort.Direction.DESC, "dailyBalanceDate"));

        return this.getMongoTemplate().find(searchTransactionsQuery, DailyBalance.class,"dailyBalances");
    }

    @Override
    public List<DailyBalance> getByAccountIdForDate(String accountId, String date) {
        Query searchTransactionsQuery = new Query(Criteria.where("accountId").is(accountId));
        searchTransactionsQuery.addCriteria(Criteria.where("dailyBalanceDate").is(date));

        return this.getMongoTemplate().find(searchTransactionsQuery, DailyBalance.class,"dailyBalances");

    }

    @Override
    public void updateAccount(DailyBalance db) {
        this.getMongoTemplate().save(db, "dailyBalances");
    }

    @Override
    public void deleteAllForAccount(String accountId) {
            Query searchTransactionsQuery = new Query(Criteria.where("accountId").is(accountId));

        this.getMongoTemplate().remove(searchTransactionsQuery, "dailyBalances");

    }

}
