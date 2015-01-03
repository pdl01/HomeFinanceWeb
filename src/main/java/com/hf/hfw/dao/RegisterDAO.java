/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hf.hfw.dao;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
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

}
