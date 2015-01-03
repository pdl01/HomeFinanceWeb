/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hf.hfw.manager;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.homefinanceshared.Account;
import java.util.List;

/**
 *
 * @author phillip.dorrell
 */
public interface AccountManager {
    public Account getAccountById(String _id);
    public Account getAccountByName(String _id);
    public Account getAccountByExternalId(String _id);
    public Account createAccount(Account account);
    public void deleteAccount(Account account);
    public Account updateAccount(Account account);
    public Account updateAccount(Account account,AccountEvent.AccountEventType _type);

    public List<Account> getAccounts();
    public List<Account> getAccountsByType(String type);
}
