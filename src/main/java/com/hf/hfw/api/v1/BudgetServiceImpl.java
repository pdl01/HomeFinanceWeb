
package com.hf.hfw.api.v1;

import com.hf.hfw.manager.BudgetManager;
import com.hf.homefinanceshared.Budget;
import com.hf.homefinanceshared.BudgetCategory;
import java.util.List;
import javax.ws.rs.Path;

/**
 *
 * @author pldorrell
 */
@Path("/budgets")
public class BudgetServiceImpl implements BudgetService {
    protected BudgetManager budgetManager;

    public BudgetManager getBudgetManager() {
        return budgetManager;
    }

    public void setBudgetManager(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }
    
    @Override
    public List<Budget> getBudgets() {
        return this.budgetManager.getBudgets();
    }

    @Override
    public Budget getById(String id) {
        return this.budgetManager.getById(id);
    }

    @Override
    public Budget saveBudget(Budget budget) throws Exception {
        if (budget.getId() != null) {    
            Budget retrievedBudget = this.budgetManager.getById(budget.getId());
            return this.budgetManager.saveBudget(budget);
        }else {
            return this.budgetManager.saveBudget(budget);
        }
    }

    @Override
    public void deleteBudget(String id) {
        Budget budget = this.budgetManager.getById(id);
        
        this.budgetManager.deleteBudget(budget);
    }

    @Override
    public Budget saveBudgetItem(String id, String type, BudgetCategory budgetCategory) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Budget deleteBudgetItem(String id, String type, BudgetCategory budgetCategory) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
