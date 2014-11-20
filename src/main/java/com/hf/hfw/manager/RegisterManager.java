
package com.hf.hfw.manager;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
import java.util.List;

/**
 *
 * @author phillip.dorrell
 */
public interface RegisterManager {
    public List<RegisterTransaction> getTransactions(Account account);
    public RegisterTransaction getTransactionById(String _id);
    public RegisterTransaction createTransaction(RegisterTransaction txn);
    public void deleteTransaction(RegisterTransaction txn);
    public RegisterTransaction updateTransaction(RegisterTransaction txn);
}
