package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.hfw.manager.RegisterManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.CategorySplit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Path;

/**
 *
 * @author phillip.dorrell
 */
@Path("/register")
public class RegisterServiceImpl implements RegisterService {
    
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
    public List<RegisterTransaction> getTransactions(String accountId, String _number, String _start) {
        Account account = new Account();
        account.setId(accountId);
        int start = Integer.parseInt(_start);
        int number = Integer.parseInt(_number);
        List<RegisterTransaction> txns = this.registerManager.getTransactions(account);
        
        if (txns != null && txns.size() > number) {
            int end = start + number;
            if (start + number > txns.size()) {
                end = txns.size();
            }
            return txns.subList(start, end);
            
        }
        return txns;
        //return this.registerManager.getTransactions(account);
        
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
            retrievedtxn.setTxnDate(transaction.getTxnDate());
            retrievedtxn.setPayee(transaction.getPayee());
            retrievedtxn.setCategorySplits(transaction.getCategorySplits());
            retrievedtxn.setTxnAmount(transaction.getTxnAmount());
            retrievedtxn.setPrimaryAccount(transaction.getPrimaryAccount());
            retrievedtxn.setCredit(transaction.isCredit());
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
    
    @Override
    public ValidationResponse validateTransaction(RegisterTransaction transaction) throws Exception {
        //do some validation
        ValidationResponse response = new ValidationResponse();
        ArrayList<String> validationMessages = new ArrayList<String>();
        response.setValid(true);
        double categorySplitSum = 0.0;
        for (CategorySplit split : transaction.getCategorySplits()) {
            categorySplitSum += split.getTxnAmount();
        }
        if (categorySplitSum != transaction.getTxnAmount()) {
            response.setValid(false);
            validationMessages.add("Split totals does not equal transaction amounts.");
            
        }
        response.setMessages(validationMessages);
        return response;
    }
    
}
