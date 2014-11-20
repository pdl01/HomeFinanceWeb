/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hf.hfw.dao;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
import java.util.List;

/**
 *
 * @author phillip.dorrell
 */
public interface RegisterDAO {
    public List<RegisterTransaction> getTransactions(Account account);
    public RegisterTransaction getTransactionById(String _id);
    public RegisterTransaction createTransaction(RegisterTransaction txn);
    public void deleteTransaction(RegisterTransaction txn);
    public RegisterTransaction updateTransaction(RegisterTransaction txn);
}
