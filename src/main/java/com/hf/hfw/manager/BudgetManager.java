/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.homefinanceshared.Budget;
import com.hf.homefinanceshared.BudgetCategory;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface BudgetManager {
        public List<Budget> getBudgets();
    public Budget getById(String id);
    public Budget createBudget(Budget budget);
    public void deleteBudget(Budget budget);
    public Budget saveBudget(Budget budget);
    public Budget addBudgetItem(Budget budget,String type,BudgetCategory category);
    public Budget deleteBudgetItem(Budget budget,String type,BudgetCategory category);
}
