package com.hf.hfw.manager;

import com.hf.hfw.dao.BudgetDAO;
import com.hf.homefinanceshared.Budget;
import com.hf.homefinanceshared.BudgetCategory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author pldorrell
 */
public class BudgetManagerImpl implements BudgetManager {

    protected Map<String, Budget> budgets;
    protected BudgetDAO budgetDAO;

    public BudgetDAO getBudgetDAO() {
        return budgetDAO;
    }

    public void setBudgetDAO(BudgetDAO budgetDAO) {
        this.budgetDAO = budgetDAO;
    }

    @Override
    public List<Budget> getBudgets() {
        return this.budgetDAO.getBudgets();
    }

    @Override
    public Budget getById(String id) {
        return this.budgetDAO.getById(id);

    }

    @Override
    public Budget createBudget(Budget budget) {
        budget.setId(UUID.randomUUID().toString());
        return this.budgetDAO.saveBudget(budget);
    }

    @Override
    public void deleteBudget(Budget budget) {
        this.budgetDAO.deleteBudget(budget);
    }

    @Override
    public Budget saveBudget(Budget budget) {
        return this.budgetDAO.saveBudget(budget);
    }

    @Override
    public Budget addBudgetItem(Budget budget, String type, BudgetCategory category) {
        if ("income".equalsIgnoreCase(type)) {

        }
        return this.saveBudget(budget);
    }

    @Override
    public Budget deleteBudgetItem(Budget budget, String type, BudgetCategory category) {
        return this.saveBudget(budget);
    }

    @Override
    public Budget addBudgetToSystem(Budget _budget) {
        if (this.budgets == null) {
            this.budgets = new HashMap<>();
        }
        this.budgets.put(_budget.getId(), _budget);
        return _budget;    
    }

}
