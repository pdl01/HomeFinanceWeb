package com.hf.hfw.manager;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.hfw.application.ApplicationState;
import com.hf.homefinanceshared.Account;
import com.hf.hfw.dao.AccountDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author phillip.dorrell
 */
public class AccountManagerImpl implements AccountManager {
    private static final Logger log = LogManager.getLogger(AccountManagerImpl.class);

    //private Map<String,Account> accounts;
    //private Map<String,String> accountNameMap;
    //private Map<String,String> accountExternalIdMap;
    
    private AccountDAO accountDAO;
    

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    @Override
    public Account getAccountById(String accountId) {
        log.debug("accountId");
        if (accountId == null) {
            return null;
        }
        Account account = this.accountDAO.getAccountById(accountId);
        return account;
        
    }

    @Override
    public Account getAccountByName(String _id) {
        if (_id == null ) {
            return null;
        }
        Account account = this.accountDAO.getAccountByName(_id);
        return account;
    }

    @Override
    public Account getAccountByExternalId(String _id) {
        if (_id == null) {
            return null;
        }
        Account account = this.getAccountByExternalId(_id);
        return account;

    }

    @Override
    public Account createAccount(Account account) {
        
        account = this.saveAccount(account);

        return account;
    }

    @Override
    public void deleteAccount(Account account) {
        this.fireAccountEvent(account, AccountEvent.AccountEventType.DELETING);
        this.accountDAO.deleteAccount(account);
        //TODO: delete all register transactions or let a handler take care of it
        this.fireAccountEvent(account, AccountEvent.AccountEventType.DELETED);
    }

    @Override
    public Account updateAccount(Account account) {
        return this.saveAccount(account);
    }
    protected Account saveAccount(Account _account) {
        _account.setLastModifiedDate(new Date());
        if (_account.getId() == null) { 
            _account.setId(UUID.randomUUID().toString());
            _account.setCreatedDate(new Date());
            Account account = this.accountDAO.createAccount(_account);
            
            //this.addAccountToSystem(_account);
            this.fireAccountEvent(_account, AccountEvent.AccountEventType.ADDED);
            return account;
        } else {
            Account oldAccount = this.getAccountById(_account.getId());
            boolean updatedStartingBalance = false;
            if (oldAccount.getStartingBalance() != _account.getStartingBalance()) {
                updatedStartingBalance = true;

            }
            Account account = this.accountDAO.updateAccount(_account);
            //this.addAccountToSystem(_account);

            this.fireAccountEvent(account, AccountEvent.AccountEventType.MODIFIED);
            if (updatedStartingBalance) {
                this.fireAccountEvent(account, AccountEvent.AccountEventType.UPDATED_STARTING_BALANCE);
            }
            return account;
        }        
    }

    @Override
    public List<Account> getAccounts() {
        
        List<Account> localAccounts = this.accountDAO.getAccounts();
        return localAccounts;
    }
     protected void fireAccountEvent(Account _account,AccountEvent.AccountEventType _type) {
        ApplicationContext appContext = ApplicationState.getApplicationState().getCtx();
	AccountEvent event = new AccountEvent(appContext, _account, _type);
	appContext.publishEvent(event);
    }

    @Override
    public Account updateAccount(Account _account, AccountEvent.AccountEventType _type) {
        Account account = this.saveAccount(_account);
        this.fireAccountEvent(account, _type);
        return account;
    }

    @Override
    public List<Account> getAccountsByType(String type) {
        //TODO use the cache for this
        return this.accountDAO.getAccountsByType(type);
    }

    @Override
    public synchronized Account addAccountToSystem(Account _account) {
        /*
        if (this.accounts == null) {
            this.accounts = new HashMap<>();
        }
        if (this.accountNameMap == null) {
            this.accountNameMap = new HashMap<>();
        }
        if (this.accountExternalIdMap == null) {
            this.accountExternalIdMap = new HashMap<>();
        }
        this.accounts.put(_account.getId(), _account);
        this.accountNameMap.put(_account.getName(),_account.getId());
        this.accountExternalIdMap.put(_account.getExternalId(),_account.getId());
        return _account;
        */
        return null;
    }
}
