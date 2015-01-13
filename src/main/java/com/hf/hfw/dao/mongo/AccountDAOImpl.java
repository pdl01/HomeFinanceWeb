package com.hf.hfw.dao.mongo;

import com.hf.homefinanceshared.Account;
import com.hf.hfw.dao.AccountDAO;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author phillip
 */
public class AccountDAOImpl extends AbstractMongoDAO implements AccountDAO {

    @Override
    public Account createAccount(Account account) {
        this.getMongoTemplate().save(account);
        return account;
    }

    @Override
    public void deleteAccount(Account account) {
        this.getMongoTemplate().remove(account);
    }

    @Override
    public Account updateAccount(Account account) {
        this.getMongoTemplate().save(account);
        return account;
    }

    @Override
    public Account getAccountById(String _id) {
        Query searchAccountQuery = new Query(Criteria.where("id").is(_id));
        return this.getMongoTemplate().findOne(searchAccountQuery, Account.class);
    }

    @Override
    public Account getAccountByName(String _name) {
        Query searchAccountQuery = new Query(Criteria.where("name").is(_name));
        return this.getMongoTemplate().findOne(searchAccountQuery, Account.class);
    }

    @Override
    public Account getAccountByExternalId(String _id) {
        Query searchAccountQuery = new Query(Criteria.where("externalId").is(_id));
        return this.getMongoTemplate().findOne(searchAccountQuery, Account.class);
    }

    @Override
    public List<Account> getAccounts() {
        return this.getMongoTemplate().findAll(Account.class);
    }

    @Override
    public List<Account> getAccountsByType(String _type) {
        Query searchAccountQuery = new Query(Criteria.where("accountType").is(_type));
        return this.getMongoTemplate().find(searchAccountQuery, Account.class);
    }
}
