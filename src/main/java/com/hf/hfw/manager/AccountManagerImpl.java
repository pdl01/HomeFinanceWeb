

package com.hf.hfw.manager;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.hfw.application.ApplicationState;
import com.hf.homefinanceshared.Account;
import com.hf.hfw.dao.AccountDAO;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author phillip.dorrell
 */
public class AccountManagerImpl implements AccountManager {
    private AccountDAO accountDAO;

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    @Override
    public Account getAccountById(String accountId) {
        return this.accountDAO.getAccountById(accountId);
    }

    @Override
    public Account getAccountByName(String _id) {
        return this.accountDAO.getAccountByName(_id);
    }

    @Override
    public Account getAccountByExternalId(String _id) {
        return this.accountDAO.getAccountByExternalId(_id);
    }

    @Override
    public Account createAccount(Account account) {
        return this.saveAccount(account);
    }

    @Override
    public void deleteAccount(Account account) {
        this.accountDAO.deleteAccount(account);    
    }

    @Override
    public Account updateAccount(Account account) {
        return this.saveAccount(account);
    }
    protected Account saveAccount(Account _account) {
        _account.setLastModifiedDate(new Date());
        if (_account.getId() == null) {
            _account.setCreatedDate(new Date());
            Account account = this.accountDAO.createAccount(_account);
            this.fireAccountEvent(account, AccountEvent.AccountEventType.ADDED);
            return account;
        } else {
            Account oldAccount = this.accountDAO.getAccountById(_account.getId());
            boolean updatedStartingBalance = false;
            if (oldAccount.getStartingBalance() != _account.getStartingBalance()) {
                updatedStartingBalance = true;

            }
            Account account = this.accountDAO.updateAccount(_account);
            this.fireAccountEvent(account, AccountEvent.AccountEventType.MODIFIED);
            if (updatedStartingBalance) {
                this.fireAccountEvent(_account, AccountEvent.AccountEventType.UPDATED_STARTING_BALANCE);
            }
            return account;
        }        
    }

    @Override
    public List<Account> getAccounts() {
        return this.accountDAO.getAccounts();
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
}
