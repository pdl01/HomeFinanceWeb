

package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.Account;
import com.hf.hfw.manager.AccountManager;
import java.util.List;
import javax.ws.rs.Path;

/**
 *
 * @author phillip.dorrell
 */
@Path("/accounts")
public class AccountServiceImpl implements AccountService {
    
    public AccountManager accountManager;

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
    
    @Override
    public List<Account> getAccounts() {
        return this.accountManager.getAccounts();
    }

    @Override
    public Account getById(String accountId) {
        return this.accountManager.getAccountById(accountId);
    }

    @Override
    public Account getByName(String name) {
        return this.accountManager.getAccountByName(name);
    }

    @Override
    public Account getByExternalId(String externalId) {
        return this.accountManager.getAccountByExternalId(externalId);
    }

    @Override
    public Account saveAccount(Account account) throws Exception {
        //Account account = this.accountRestConverter.convert(_account);
        //TODO validation!!
        if (account.getId() != null) {
            //TODO validation on existing !!
            //name validation
            //externalId validation
            
            //copy of the existing values not passed in
            Account retrievedAccount = this.accountManager.getAccountById(account.getId());
            account.setCurrentBalance(retrievedAccount.getCurrentBalance());
            account.setBalanceLastCalculatedDate(retrievedAccount.getBalanceLastCalculatedDate());
            account.setCreatedDate(retrievedAccount.getCreatedDate());
            account.setLastModifiedDate(retrievedAccount.getLastModifiedDate());
            account.setLastImportActionDate(retrievedAccount.getLastImportActionDate());
            account.setLastImportedTransactionDate(retrievedAccount.getLastImportedTransactionDate());
            return this.accountManager.updateAccount(account);
        } else {
            //TODO validation on existing !!
            //name validation
            //externalId validation
            Account retrievedAccount = this.accountManager.getAccountByName(account.getName());
            if (retrievedAccount != null) {
                throw new Exception("Account with name already exists");
            }
            return this.accountManager.createAccount(account);
        }
        
        //Account savedAccount = this.accountManager.save(account);
        //return savedAccount;
        //return this.accountRestConverter.convert(savedAccount);
    
    }

    @Override
    public void deleteAccount(String accountId) {
        Account account = this.accountManager.getAccountById(accountId);
        if (account != null) {
           this.accountManager.deleteAccount(account);
        }
    }

    @Override
    public List<Account> getAccountsByType(String type) {
return this.accountManager.getAccountsByType(type);
    }
    
}
