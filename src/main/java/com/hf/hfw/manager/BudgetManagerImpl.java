/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.dao.BudgetDAO;
import com.hf.homefinanceshared.Budget;
import com.hf.homefinanceshared.BudgetCategory;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public class BudgetManagerImpl implements BudgetManager {

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

}
