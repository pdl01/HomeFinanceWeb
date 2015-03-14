
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
    public List<RegisterTransaction> getTransactions(Account account,int start, int number);
    public List<RegisterTransaction> getTransactionsByCategories(Account account,List<String> categories);
    public RegisterTransaction getTransactionById(String _id);
    public RegisterTransaction createTransaction(RegisterTransaction txn);
    public void deleteTransaction(RegisterTransaction txn);
    public RegisterTransaction updateTransaction(RegisterTransaction txn);
    public List<RegisterTransaction> getTransactionsByCategoriesStartsWithForDateStartWith(Account account, String category,String date);

    public List<RegisterTransaction> getTransactionsForDateStartWith(Account account, String date, boolean getCredit);
    public List<RegisterTransaction> getAllTransactionsForDateStartWith(Account account, String date);
    public void addPendingTransactions(List<RegisterTransaction> txns);
    public void addPendingTransactions(RegisterTransaction txn);
    public List<RegisterTransaction> getPendingTransactions(Account account);
    public RegisterTransaction getPendingTransactionById(String id);
    public List<RegisterTransaction> matchTransaction(RegisterTransaction pendingTransaction);

}
