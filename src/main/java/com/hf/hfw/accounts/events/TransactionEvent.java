/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.accounts.events;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author phillip.dorrell
 */
public class TransactionEvent extends ApplicationEvent{
    private TransactionEventType type;
    private RegisterTransaction registerTransaction;
    
    public enum TransactionEventType {
        ADDED(1),MODIFIED(2),DELETING(3),DELETED(4),UPDATED_TRANSACTIONS(5),UPDATED_BALANCE(6),UPDATED_STARTING_BALANCE(7);
        private int code;
	private TransactionEventType(int _code) {
            this.code = _code;
	}
	public int getCode() {
            return code;
	}
    }
    
    public TransactionEvent(Object _source,RegisterTransaction _registerTransaction,TransactionEventType _type) {
	super(_source);
        this.registerTransaction = _registerTransaction;
        this.type = _type;
    }

    public TransactionEventType getType() {
        return type;
    }

    public void setType(TransactionEventType type) {
        this.type = type;
    }

    public RegisterTransaction getRegisterTransaction() {
        return registerTransaction;
    }

    public void setRegisterTransaction(RegisterTransaction registerTransaction) {
        this.registerTransaction = registerTransaction;
    }
    
    
    
}
