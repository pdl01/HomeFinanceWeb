package com.hf.hfw.dao;

import com.hf.homefinanceshared.Budget;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface BudgetDAO {
    public List<Budget> getBudgets();
    public Budget getById(String id);
    public Budget createBudget(Budget budget);
    public void deleteBudget(Budget budget);
    public Budget saveBudget(Budget budget);
}
