/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.homefinanceshared.DailyBalance;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface DailyBalanceManager {

    public List<DailyBalance> getByAccountId(String accountId);

    public List<DailyBalance> getByAccountIdForDate(String accountId, String date);

    public void updateAccount(DailyBalance db);

    public void deleteAllForAccount(String accountId);
    public void buildForAccount(String accountId);
}
