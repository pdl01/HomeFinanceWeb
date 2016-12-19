package com.hf.hfw.dao.lwmds;

import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.dao.AccountDAO;
import com.hf.hfw.dao.lwmds.converter.AccountConverter;

import com.hf.homefinanceshared.Account;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.DataStore;
import com.hf.lwdatastore.exception.CollectionNotFoundException;
import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


/**
 *
 * @author pldorrell
 */
public class AccountDAOImpl extends LWMDSDAO implements AccountDAO{
    private static final Logger log = Logger.getLogger(AccountDAOImpl.class);

    
    @Override
    public Account getAccountById(String _id) {
        try {
            CollectionObject cObject = getDataStore().getObject(ConfigBuilder.COLLECTION_ACCOUNTS, _id);
            if (cObject != null) {
                return (new AccountConverter()).convertFromCollectionObject(cObject);
            }
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return null;
    }

    @Override
    public Account getAccountByName(String _id) {
        try {
            List<CollectionObject> cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_ACCOUNTS, "name", _id);
            if (cObjects != null && cObjects.size() > 0) {
                return (new AccountConverter()).convertFromCollectionObject(cObjects.get(0));
            }
        }catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);
        }catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return null;

    }

    @Override
    public Account getAccountByExternalId(String _id) {
        try {
            List<CollectionObject> cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_ACCOUNTS, "externalId", _id);
            if (cObjects != null && cObjects.size() > 0) {
                return (new AccountConverter()).convertFromCollectionObject(cObjects.get(0));
            }
        }catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);
        }catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return null;

    }

    @Override
    public Account createAccount(Account account) {
        try {
            getDataStore().putObject(ConfigBuilder.COLLECTION_ACCOUNTS, new CollectionObject(account), new AccountConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return account;
    }

    @Override
    public void deleteAccount(Account account) {
        try {
            getDataStore().removeObject(ConfigBuilder.COLLECTION_ACCOUNTS, account.getId());
            //TODO: delete all transactions in that account
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
    }

    @Override
    public Account updateAccount(Account account) {
     try {
            getDataStore().putObject(ConfigBuilder.COLLECTION_ACCOUNTS, new CollectionObject(account), new AccountConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        ArrayList<Account> al = new ArrayList<Account>();
        try {
            List<CollectionObject> cObjects = getDataStore().getObjects(ConfigBuilder.COLLECTION_ACCOUNTS);
            if (cObjects != null && cObjects.size() > 0) {
                AccountConverter aConverter = new AccountConverter();
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
    public List<Account> getAccountsByType(String _type) {
        ArrayList<Account> al = new ArrayList<Account>();
        try {
            List<CollectionObject> cObjects = getDataStore().getByIndex(ConfigBuilder.COLLECTION_ACCOUNTS, "accountType", _type);
            if (cObjects != null && cObjects.size() > 0) {
                AccountConverter aConverter = new AccountConverter();
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
        return null;        }
    
}
