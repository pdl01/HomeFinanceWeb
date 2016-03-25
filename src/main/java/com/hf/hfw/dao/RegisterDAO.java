/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hf.hfw.dao;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.OnlineTransaction;
import com.hf.homefinanceshared.RegisterTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author phillip.dorrell
 */
public interface RegisterDAO {
    public List<RegisterTransaction> getTransactions(Account account);
    public List<RegisterTransaction> getTransactionsByCategories(Account account,List<String> categories);

    public RegisterTransaction getTransactionById(String _id);
    public RegisterTransaction createTransaction(RegisterTransaction txn);
    public void deleteTransaction(RegisterTransaction txn);
    public RegisterTransaction updateTransaction(RegisterTransaction txn);
    public List<RegisterTransaction> getTransactionsByCategoriesStartsWithForDateStartWith(Account account, String category, String date);
    public Set<String> getAllCategories();
    public List<RegisterTransaction> getTransactionsForDateStartWith(Account account, String date, boolean getCredit);
    public void addPendingTransactions(List<OnlineTransaction> txns);
    public List<OnlineTransaction> getPendingTransactions(Account account);
    public List<RegisterTransaction> getAllTransactionsForDateStartWith(Account account, String date);
    public OnlineTransaction getPendingTransactionById(String id);
    public List<RegisterTransaction> matchTransaction(OnlineTransaction pendingTransaction);
    public List<RegisterTransaction> findTransaction(String searchTerm);
    public List<RegisterTransaction> findTransaction(Account account, String searchTerm);
    public Set<String> getAllNames();
}
