package com.hf.hfw.dao.lwmds;

import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.dao.ScheduledTransactionDAO;
import com.hf.hfw.dao.lwmds.converter.ScheduledTransactionConverter;
import com.hf.hfw.dao.lwmds.converter.TransactionConverter;
import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.homefinanceshared.ScheduledTransaction;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.DataStore;
import com.hf.lwdatastore.exception.CollectionNotFoundException;
import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author pldorrell
 */
public class ScheduleTransactionDAOImpl implements ScheduledTransactionDAO {

    private static final Logger log = Logger.getLogger(RegisterDAOImpl.class);

    private DataStore getDataStore() {
        return ApplicationState.getApplicationState().getDbFactory().getLwDataStore();
    }

    @Override
    public ScheduledTransaction getById(String _id) {
        try {
            CollectionObject cObject = getDataStore().getObject(ConfigBuilder.COLLECTION_SCHEDULED_TRANSACTIONS, _id);
            if (cObject != null) {
                return (new ScheduledTransactionConverter()).convertFromCollectionObject(cObject);
            }
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);
        }
        return null;

    }

    @Override
    public ScheduledTransaction createTransaction(ScheduledTransaction txn) {
        return this.updateTransaction(txn);
    }

    @Override
    public void deleteTransaction(ScheduledTransaction txn) {
        try {
            getDataStore().removeObject(ConfigBuilder.COLLECTION_SCHEDULED_TRANSACTIONS, txn.getId());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);
        }

    }

    @Override
    public ScheduledTransaction updateTransaction(ScheduledTransaction txn) {
        try {
            getDataStore().putObject(ConfigBuilder.COLLECTION_SCHEDULED_TRANSACTIONS, new CollectionObject(txn), new ScheduledTransactionConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);
        }
        return txn;

    }

    @Override
    public List<ScheduledTransaction> getOriginalTransactionsByAccountId(String accountId) {


        HashMap<String, String> queryIndexes = new HashMap<String, String>();
        queryIndexes.put("accountId", accountId);
        queryIndexes.put("original", "true");

        List<CollectionObject> cObjects;
        try {
            cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_SCHEDULED_TRANSACTIONS, queryIndexes);
            ArrayList<ScheduledTransaction> rt = new ArrayList<>();
            ScheduledTransactionConverter stc = new ScheduledTransactionConverter();
            for (CollectionObject collectionObject : cObjects) {
                ScheduledTransaction txn = stc.convertFromCollectionObject(collectionObject);
                rt.add(txn);
            }
            return rt;

        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found", ex);

        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);

        }
        return null;
        
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsByAccountId(String accountId) {
        HashMap<String, String> queryIndexes = new HashMap<String, String>();
        queryIndexes.put("accountId", accountId);
        queryIndexes.put("original", "false");

        List<CollectionObject> cObjects;
        try {
            cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_SCHEDULED_TRANSACTIONS, queryIndexes);
            ArrayList<ScheduledTransaction> rt = new ArrayList<>();
            ScheduledTransactionConverter stc = new ScheduledTransactionConverter();
            for (CollectionObject collectionObject : cObjects) {
                ScheduledTransaction txn = stc.convertFromCollectionObject(collectionObject);
                rt.add(txn);
            }
            return rt;

        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found", ex);

        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);

        }
        return null;

    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsByAccountIdForTimePeriod(String accountId, String timePeriod) {

        boolean useMonth = false;
        HashMap<String, String> queryIndexes = new HashMap<String, String>();
        queryIndexes.put("accountId", accountId);
        queryIndexes.put("original", "false");
        if (timePeriod.length() == 7) {
            useMonth = true;
        }
        if (useMonth) {
            queryIndexes.put("month", timePeriod);
        } else {
            queryIndexes.put("year", timePeriod);
        }
        List<CollectionObject> cObjects;
        try {
            cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_SCHEDULED_TRANSACTIONS, queryIndexes);
            ArrayList<ScheduledTransaction> rt = new ArrayList<>();
            ScheduledTransactionConverter stc = new ScheduledTransactionConverter();
            for (CollectionObject collectionObject : cObjects) {
                ScheduledTransaction txn = stc.convertFromCollectionObject(collectionObject);
                rt.add(txn);
            }
            return rt;

        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found", ex);

        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);

        }
        return null;
    }

    @Override
    public List<ScheduledTransaction> getLatestUpcomingTransactionsByAccountId(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated mehods, choose Tools | Templates.
    }

    @Override
    public ScheduledTransaction getLatestUpcomingTransactionsByOriginalTxnId(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ScheduledTransaction> getUpcomingTransactionsByOriginalTxnId(String originalTransactionId) {
        HashMap<String, String> queryIndexes = new HashMap<String, String>();
        queryIndexes.put("originalTransactionId", originalTransactionId);
        queryIndexes.put("original", "false");

        List<CollectionObject> cObjects;
        try {
            cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_SCHEDULED_TRANSACTIONS, queryIndexes);
            ArrayList<ScheduledTransaction> rt = new ArrayList<>();
            ScheduledTransactionConverter stc = new ScheduledTransactionConverter();
            for (CollectionObject collectionObject : cObjects) {
                ScheduledTransaction txn = stc.convertFromCollectionObject(collectionObject);
                rt.add(txn);
            }
            return rt;

        } catch (IndexNotFoundException ex) {
            log.error("Index Not Found", ex);

        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);

        }
        return null;

    }

}
