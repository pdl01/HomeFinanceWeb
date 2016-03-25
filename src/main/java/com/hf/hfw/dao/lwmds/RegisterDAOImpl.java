/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.lwmds;

import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.dao.RegisterDAO;
import com.hf.hfw.dao.lwmds.converter.NotificationConverter;
import com.hf.hfw.dao.lwmds.converter.TransactionConverter;
import com.hf.hfw.notifications.Notification;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.OnlineTransaction;
import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.DataStore;
import com.hf.lwdatastore.exception.CollectionNotFoundException;
import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author pldorrell
 */
public class RegisterDAOImpl implements RegisterDAO {
    private static final Logger log = Logger.getLogger(RegisterDAOImpl.class);
    private DataStore getDataStore() {
        return ApplicationState.getApplicationState().getDbFactory().getLwDataStore();
    }
    @Override
    public List<RegisterTransaction> getTransactions(Account account) {
        ArrayList<RegisterTransaction> al = new ArrayList<RegisterTransaction>();
        try {
            List<CollectionObject> cObjects = getDataStore().getByIndex("transactions", "accountId", account.getId());
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
                List<CollectionObject> cObjects = getDataStore().getByIndex("transactions", queryIndexes);
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
            CollectionObject cObject = getDataStore().getObject("transactions", _id);
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
            getDataStore().removeObject("transactions", txn.getId());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
    }

    @Override
    public RegisterTransaction updateTransaction(RegisterTransaction txn) {
     try {
            getDataStore().putObject("transactions", new CollectionObject(txn), new TransactionConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return txn;
    }

    @Override
    public List<RegisterTransaction> getTransactionsByCategoriesStartsWithForDateStartWith(Account account, String category, String date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> getAllCategories() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            cObjects = getDataStore().getByIndex("transactions",queryIndexes);
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
    public void addPendingTransactions(List<OnlineTransaction> txns) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<OnlineTransaction> getPendingTransactions(Account account) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
            cObjects = getDataStore().getByIndex("transactions",queryIndexes);
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
    public OnlineTransaction getPendingTransactionById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RegisterTransaction> matchTransaction(OnlineTransaction pendingTransaction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
