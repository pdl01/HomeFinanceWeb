
package com.hf.hfw.accounts.listeners;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.homefinanceshared.Account;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author phillip.dorrell
 */
public class AccountBalanceListener extends BalanceListener implements ApplicationListener<AccountEvent>{
    

    
    
    @Override
    public void onApplicationEvent(AccountEvent e) {
        if (e.getType() == AccountEvent.AccountEventType.ADDED || 
                e.getType() == AccountEvent.AccountEventType.UPDATED_TRANSACTIONS ||
                e.getType() == AccountEvent.AccountEventType.UPDATED_STARTING_BALANCE
                ) {
            
            Account account = this.accountManager.getAccountById(e.getAccount().getId());
            super.rebalanceAccount(account);

        }
    }
    
    
}
