/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.logging.Level;
import org.apache.log4j.Logger;


/**
 *
 * @author pldorrell
 */
public class AccountDAOImpl implements AccountDAO{
    private static final Logger log = Logger.getLogger(AccountDAOImpl.class);
    private DataStore getDataStore() {
        return ApplicationState.getApplicationState().getDbFactory().getLwDataStore();
    }
    
    @Override
    public Account getAccountById(String _id) {
        try {
            CollectionObject cObject = getDataStore().getObject("account", _id);
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
            List<CollectionObject> cObjects = getDataStore().getByIndex("account", "name", _id);
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
            List<CollectionObject> cObjects = getDataStore().getByIndex("account", "externalId", _id);
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
            getDataStore().putObject("account", new CollectionObject(account), new AccountConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return account;
    }

    @Override
    public void deleteAccount(Account account) {
        try {
            getDataStore().removeObject("account", account.getId());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
    }

    @Override
    public Account updateAccount(Account account) {
     try {
            getDataStore().putObject("account", new CollectionObject(account), new AccountConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        ArrayList<Account> al = new ArrayList<Account>();
        try {
            List<CollectionObject> cObjects = getDataStore().getObjects("account");
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
            List<CollectionObject> cObjects = getDataStore().getByIndex("account", "accountType", _type);
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
