package com.hf.hfw.dao.lwmds;

import com.hf.hfw.dao.RegisterDAO;
import com.hf.hfw.dao.lwmds.converter.OnlineTransactionConverter;
import com.hf.hfw.dao.lwmds.converter.TransactionConverter;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.CategorySplit;
import com.hf.homefinanceshared.OnlineTransaction;
import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.exception.CollectionNotFoundException;
import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

/**
 *
 * @author pldorrell
 */
public class RegisterDAOImpl extends LWMDSDAO implements RegisterDAO {
    private static final Logger log = Logger.getLogger(RegisterDAOImpl.class);

    @Override
    public List<RegisterTransaction> getTransactions(Account account) {
        ArrayList<RegisterTransaction> al = new ArrayList<RegisterTransaction>();
        try {
            List<CollectionObject> cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_TRANSACTIONS, "accountId", account.getId());
            if (cObjects != null && cObjects.size() > 0) {
                TransactionConverter aConverter = new TransactionConverter();
                for (CollectionObject collectionObject: cObjects) {
                    al.add(aConverter.convertFromCollectionObject(collectionObject));
                }
            }
            return al;
        }catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);
        }
        return null;    
    }

    @Override
    public List<RegisterTransaction> getTransactionsByCategories(Account account, List<String> categories) {
        ArrayList<RegisterTransaction> al = new ArrayList<RegisterTransaction>();
        TreeSet<RegisterTransaction> txnSet = new TreeSet<RegisterTransaction>();
        TransactionConverter aConverter = new TransactionConverter();
        HashMap<String,String> queryIndexes = new HashMap<String,String>();
        queryIndexes.put("accountId",account.getId());

        try {
            for (String category:categories) {
                queryIndexes.put("category",category);
                List<CollectionObject> cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_TRANSACTIONS, queryIndexes);
                for (CollectionObject collectionObject: cObjects) {
                    txnSet.add(aConverter.convertFromCollectionObject(collectionObject));
                }         
            }
            if (txnSet != null) {
                al.addAll(txnSet);
            }
            return al;
        }catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);
        }catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return al;
    }

    @Override
    public RegisterTransaction getTransactionById(String _id) {
        try {
            CollectionObject cObject = getDataStore().getObject(ConfigBuilder.COLLECTION_TRANSACTIONS, _id);
            if (cObject != null) {
                return (new TransactionConverter()).convertFromCollectionObject(cObject);
            }
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return null;    
    }

    @Override
    public RegisterTransaction createTransaction(RegisterTransaction txn) {
        return this.updateTransaction(txn);
    }

    @Override
    public void deleteTransaction(RegisterTransaction txn) {
        try {
            getDataStore().removeObject(ConfigBuilder.COLLECTION_TRANSACTIONS, txn.getId());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
    }

    @Override
    public RegisterTransaction updateTransaction(RegisterTransaction txn) {
     try {
            getDataStore().putObject(ConfigBuilder.COLLECTION_TRANSACTIONS, new CollectionObject(txn), new TransactionConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return txn;
    }
    private boolean hasCategory(RegisterTransaction txn,String category) {
        if (txn.getCategorySplits() != null && txn.getCategorySplits().length > 0) {
            for (CategorySplit categorySplit: txn.getCategorySplits()) {
                if (categorySplit.getCategory().equals(category)) {
                    return true;
                }
            }
        }
            return false;
        
    }
    @Override
    public List<RegisterTransaction> getTransactionsByCategoriesStartsWithForDateStartWith(Account account, String category, String date) {
        boolean useMonth = false;
        HashMap<String,String> queryIndexes = new HashMap<String,String>();
        queryIndexes.put("accountId",account.getId());
        
        if (date.length() == 7) {
            useMonth = true;
        } 
        if (useMonth) {
            queryIndexes.put("month", date);
        } else {
            queryIndexes.put("year", date);
        }
        List<CollectionObject> cObjects;
        try {
            cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_TRANSACTIONS,queryIndexes);
            ArrayList<RegisterTransaction> rt = new ArrayList<RegisterTransaction>();
            TransactionConverter tc = new TransactionConverter();
            for (CollectionObject collectionObject:cObjects) {
                RegisterTransaction txn = tc.convertFromCollectionObject(collectionObject);
                if (this.hasCategory(txn, category)) {
                    rt.add(txn);    
                }
                
            }
            return rt;

        
        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);

        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);

        }
        return null;
    }

    @Override
    public Set<String> getAllCategories() {
        List<RegisterTransaction> txns = this.getAllTransactions();
        TreeSet<String> categories = new TreeSet<>();
        for (RegisterTransaction txn:txns) {
            if (txn.getCategory() != null) {
                categories.add(txn.getCategory());
            }
            if (txn.getCategorySplits().length > 0) {
                for (CategorySplit split:txn.getCategorySplits()) {
                    if (split.getCategory() != null) {
                        categories.add(split.getCategory());
                    }
                }
            }
        }
        return categories;
    }

    @Override
    public List<RegisterTransaction> getTransactionsForDateStartWith(Account account, String date, boolean getCredit) {
        boolean useMonth = false;
        HashMap<String,String> queryIndexes = new HashMap<String,String>();
        queryIndexes.put("accountId",account.getId());
        
        if (date.length() == 7) {
            useMonth = true;
        } 
        if (useMonth) {
            queryIndexes.put("month", date);
        } else {
            queryIndexes.put("year", date);
        }
        List<CollectionObject> cObjects;
        try {
            cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_TRANSACTIONS,queryIndexes);
            ArrayList<RegisterTransaction> rt = new ArrayList<>();
            TransactionConverter tc = new TransactionConverter();
            for (CollectionObject collectionObject:cObjects) {
                RegisterTransaction transaction = tc.convertFromCollectionObject(collectionObject);
                if (transaction.isCredit() == getCredit) {
                    rt.add(transaction);
                }                
            }
            return rt;

        
        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);

        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);

        }
        return null;
    }

    private void addPendingTransaction(OnlineTransaction txn) {
             try {
            getDataStore().putObject(ConfigBuilder.COLLECTION_ONLINE_TRANSACTIONS, new CollectionObject(txn), new OnlineTransactionConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        
    }
    
    @Override
    public void addPendingTransactions(List<OnlineTransaction> txns) {
        for (OnlineTransaction txn:txns) {
            this.addPendingTransaction(txn);
        }
    }

    @Override
    public List<OnlineTransaction> getPendingTransactions(Account account) {
        ArrayList<OnlineTransaction> al = new ArrayList<OnlineTransaction>();
        try {
            List<CollectionObject> cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_ONLINE_TRANSACTIONS, "accountId", account.getId());
            if (cObjects != null && cObjects.size() > 0) {
                OnlineTransactionConverter aConverter = new OnlineTransactionConverter();
                for (CollectionObject collectionObject: cObjects) {
                    al.add(aConverter.convertFromCollectionObject(collectionObject));
                }
            }
            return al;
        }catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);
        }
        return null;    }

    @Override
    public List<RegisterTransaction> getAllTransactionsForDateStartWith(Account account, String date) {
        boolean useMonth = false;
        HashMap<String,String> queryIndexes = new HashMap<String,String>();
        queryIndexes.put("accountId",account.getId());
        
        if (date.length() == 7) {
            useMonth = true;
        } 
        if (useMonth) {
            queryIndexes.put("month", date);
        } else {
            queryIndexes.put("year", date);
        }
        List<CollectionObject> cObjects;
        try {
            cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_TRANSACTIONS,queryIndexes);
            ArrayList<RegisterTransaction> rt = new ArrayList<RegisterTransaction>();
            TransactionConverter tc = new TransactionConverter();
            for (CollectionObject collectionObject:cObjects) {
                rt.add(tc.convertFromCollectionObject(collectionObject));
            }
            return rt;

        
        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);

        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);

        }
        return null;

    }

    @Override
    public OnlineTransaction getPendingTransactionById(String _id) {
        try {
            CollectionObject cObject = getDataStore().getObject(ConfigBuilder.COLLECTION_ONLINE_TRANSACTIONS, _id);
            if (cObject != null) {
                return (new OnlineTransactionConverter()).convertFromCollectionObject(cObject);
            }
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return null;     
    }

    @Override
    public List<RegisterTransaction> matchTransaction(OnlineTransaction pendingTransaction) {
        HashMap<String,String> queryIndexes = new HashMap<String,String>();
        queryIndexes.put("accountId",pendingTransaction.getPrimaryAccount());
        queryIndexes.put("statusTxt", RegisterTransaction.STATUS_NONE);
        


        List<CollectionObject> cObjects;
        try {
            cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_TRANSACTIONS,queryIndexes);
            ArrayList<RegisterTransaction> rt = new ArrayList<RegisterTransaction>();
            TransactionConverter tc = new TransactionConverter();
            for (CollectionObject collectionObject:cObjects) {
                RegisterTransaction txn = tc.convertFromCollectionObject(collectionObject);
                if (txn.getTxnAmount() == pendingTransaction.getTxnAmount()){
                    rt.add(txn);    
                }
                
  
            }
            return rt;

        
        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);

        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);

        }
        return null;
    }

    @Override
    public List<RegisterTransaction> findTransaction(String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RegisterTransaction> findTransaction(Account account, String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> getAllNames() {
        List<RegisterTransaction> txns = this.getAllTransactions();
        TreeSet<String> names = new TreeSet<>();
        for (RegisterTransaction txn:txns) {
            names.add(txn.getPayee());
        }
        return names;
    }

    @Override
    public List<RegisterTransaction> getAllTransactions() {
        ArrayList<RegisterTransaction> al = new ArrayList<RegisterTransaction>();
        try {
            List<CollectionObject> cObjects = getDataStore().getObjects(ConfigBuilder.COLLECTION_TRANSACTIONS);
            if (cObjects != null && cObjects.size() > 0) {
                TransactionConverter aConverter = new TransactionConverter();
                for (CollectionObject collectionObject: cObjects) {
                    al.add(aConverter.convertFromCollectionObject(collectionObject));
                }
            }
            return al;
        }catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return null;    


    }

    @Override
    public void deleteOnlineTransaction(OnlineTransaction txn) {
        try {
            getDataStore().removeObject(ConfigBuilder.COLLECTION_ONLINE_TRANSACTIONS, txn.getId());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }

    }
    
}
