package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.hfw.manager.RegisterManager;
import com.hf.homefinanceshared.Account;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Path;

/**
 *
 * @author phillip.dorrell
 */
@Path("/register")
public class RegisterServiceImpl implements RegisterService{
    
    protected RegisterManager registerManager;

    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }
    
    @Override
    public List<RegisterTransaction> getTransactions(String accountId) {
        Account account = new Account();
        account.setId(accountId);
        return this.registerManager.getTransactions(account);
        
    }

    @Override
    public RegisterTransaction getTransactionById(String transactionId) {
        RegisterTransaction txn = this.registerManager.getTransactionById(transactionId);
        return txn;
    }

    @Override
    public RegisterTransaction saveTransaction(RegisterTransaction transaction) throws Exception {
        if (transaction.getId() != null) {
            RegisterTransaction retrievedtxn = this.registerManager.getTransactionById(transaction.getId());
            
            //transaction.setCreatedDate(retrievedtxn.getCreatedDate());
            //transaction.setLastModifiedDate(retrievedtxn.getLastModifiedDate());
            retrievedtxn.setPayee(transaction.getPayee());
            retrievedtxn.setCategorySplits(transaction.getCategorySplits());
            retrievedtxn.setTxnAmount(transaction.getTxnAmount());
            retrievedtxn.setPrimaryAccount(transaction.getPrimaryAccount());
            return this.registerManager.updateTransaction(retrievedtxn);
        } else {
            return this.registerManager.createTransaction(transaction);
        }
    }

    @Override
    public void deleteTransaction(String transactionId) {
        RegisterTransaction txn = this.registerManager.getTransactionById(transactionId);
        if (txn != null) {
           this.registerManager.deleteTransaction(txn);
        }    
    }
    
}
