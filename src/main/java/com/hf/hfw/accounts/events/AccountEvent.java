/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.accounts.events;

import com.hf.homefinanceshared.Account;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author phillip.dorrell
 */
public class AccountEvent extends ApplicationEvent{
    private AccountEventType type;
    private Account account;
    
    public enum AccountEventType {
        ADDED(1),MODIFIED(2),DELETING(3),DELETED(4),UPDATED_TRANSACTIONS(5),UPDATED_BALANCE(6),UPDATED_STARTING_BALANCE(7),UPLOADED_TRANSACTION_FILE(8);
        private int code;
	private AccountEventType(int _code) {
            this.code = _code;
	}
	public int getCode() {
            return code;
	}
    }
    
    public AccountEvent(Object _source,Account _account,AccountEventType _type) {
	super(_source);
        this.account = _account;
        this.type = _type;
    }

    public AccountEventType getType() {
        return type;
    }

    public void setType(AccountEventType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    
    
}
