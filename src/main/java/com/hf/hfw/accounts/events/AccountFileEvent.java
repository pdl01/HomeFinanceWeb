/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.accounts.events;

import com.hf.homefinanceshared.Account;

/**
 *
 * @author pldorrell
 */
public class AccountFileEvent extends AccountEvent {
    private String fileName;
    public AccountFileEvent(Object _source, Account _account, AccountEventType _type) {
        super(_source, _account, _type);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}
