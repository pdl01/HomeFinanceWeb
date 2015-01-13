package com.hf.hfw.dao.mongo;

import com.hf.hfw.dao.BudgetDAO;
import com.hf.homefinanceshared.Budget;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author pldorrell
 */
public class BudgetDAOImpl extends AbstractMongoDAO implements BudgetDAO {

    @Override
    public List<Budget> getBudgets() {
        return this.getMongoTemplate().findAll(Budget.class);
    }

    @Override
    public Budget getById(String id) {
        Query searchBudgetQuery = new Query(Criteria.where("id").is(id));
        return this.getMongoTemplate().findOne(searchBudgetQuery, Budget.class);
    }

    @Override
    public Budget createBudget(Budget budget) {
        this.getMongoTemplate().save(budget);
        return budget;
    }

    @Override
    public void deleteBudget(Budget budget) {
        this.getMongoTemplate().remove(budget);
    }

    @Override
    public Budget saveBudget(Budget budget) {
        this.getMongoTemplate().save(budget);
        return budget;
    }

}
