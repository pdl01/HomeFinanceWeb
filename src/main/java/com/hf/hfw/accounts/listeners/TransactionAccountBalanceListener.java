/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.accounts.listeners;

import com.hf.hfw.accounts.events.TransactionEvent;
import com.hf.homefinanceshared.Account;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author phillip.dorrell
 */
public class TransactionAccountBalanceListener extends BalanceListener implements ApplicationListener<TransactionEvent> {

    @Override
    public void onApplicationEvent(TransactionEvent e) {
        if (e.getType() == TransactionEvent.TransactionEventType.ADDED
                || e.getType() == TransactionEvent.TransactionEventType.DELETED
                || e.getType() == TransactionEvent.TransactionEventType.MODIFIED) {
            Account account = this.accountManager.getAccountById(e.getRegisterTransaction().getPrimaryAccount());
            super.rebalanceAccount(account);
        }
    }

}
