
package com.hf.hfw.dao.mongo;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.hfw.dao.RegisterDAO;
import com.hf.homefinanceshared.CategorySplit;
import com.hf.homefinanceshared.OnlineTransaction;
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
    private static String COLLECTION_NAME_ONLINETXNS="onlineData";
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
            if (txn.getCategorySplits() != null) {
                try {
                    for (CategorySplit split : txn.getCategorySplits()) {
                        categories.add(split.getCategory());
                        log.info("Adding "+split.getCategory());
                    }
                } catch (Exception e) {
                    log.error("Error encountered in evaluting category split", e);
                }
                
            } else {
                log.info("Skipping txn; no categories");
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
    public void addPendingTransactions( List<OnlineTransaction> txns) {
        for (OnlineTransaction txn: txns) {
            this.getMongoTemplate().save(txn, COLLECTION_NAME_ONLINETXNS);    
        }
        
    }

    @Override
    public List<OnlineTransaction> getPendingTransactions(Account account) {
        Query searchTransactionsQuery = new Query(Criteria.where("primaryAccount").is(account.getId()));
        searchTransactionsQuery.addCriteria(Criteria.where("statusTxt").is(RegisterTransaction.STATUS_IMPORTED));
        searchTransactionsQuery.with(new Sort(Sort.Direction.ASC,"txnDate"));

        return this.getMongoTemplate().find(searchTransactionsQuery, OnlineTransaction.class, COLLECTION_NAME_ONLINETXNS);
    }

    @Override
    public OnlineTransaction getPendingTransactionById(String _id) {
        Query searchTransactionQuery = new Query(Criteria.where("id").is(_id));
        return this.getMongoTemplate().findOne(searchTransactionQuery, OnlineTransaction.class,COLLECTION_NAME_ONLINETXNS);
    }

    @Override
    public List<RegisterTransaction> matchTransaction(OnlineTransaction pendingTransaction) {
        Query searchTransactionQuery = new Query(Criteria.where("txnAmount").is(pendingTransaction.getTxnAmount()));
        searchTransactionQuery.addCriteria(Criteria.where("statusTxt").is(RegisterTransaction.STATUS_NONE));
        searchTransactionQuery.addCriteria(Criteria.where("primaryAccount").is(pendingTransaction.getPrimaryAccount()));
        return this.getMongoTemplate().find(searchTransactionQuery, RegisterTransaction.class);
    }

    @Override
    public List<RegisterTransaction> findTransaction(String searchTerm) {
        Query searchTransactionQuery = new Query(Criteria.where("payee").regex("^" + searchTerm));
        searchTransactionQuery.with(new Sort(Sort.Direction.DESC,"txnDate"));
        
        return this.getMongoTemplate().find(searchTransactionQuery, RegisterTransaction.class);
        
    }

    @Override
    public List<RegisterTransaction> findTransaction(Account account, String searchTerm) {
        Query searchTransactionQuery = new Query(Criteria.where("payee").regex("^" + searchTerm));
        searchTransactionQuery.addCriteria(Criteria.where("primaryAccount").is(account.getId()));
        searchTransactionQuery.with(new Sort(Sort.Direction.DESC,"txnDate"));
        
        return this.getMongoTemplate().find(searchTransactionQuery, RegisterTransaction.class);

    }

    @Override
    public Set<String> getAllNames() {
        List<RegisterTransaction> txns = this.getMongoTemplate().findAll(RegisterTransaction.class);
        TreeSet<String> names = new TreeSet<String>();
        for (RegisterTransaction txn : txns) {
            if (txn.getPayee() != null) {
                names.add(txn.getPayee());                
            } else {
                log.info("Skipping txn; no payee");
            }
        }
        return names;

    }

    @Override
    public List<RegisterTransaction> getAllTransactions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteOnlineTransaction(OnlineTransaction txn) {
        this.getMongoTemplate().remove(txn,COLLECTION_NAME_ONLINETXNS);
    }

    
}
