package com.hf.hfw.dao.lwmds;

import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.dao.BudgetDAO;
import com.hf.hfw.dao.lwmds.converter.BudgetConverter;
import com.hf.hfw.dao.lwmds.converter.TransactionConverter;
import com.hf.homefinanceshared.Budget;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.DataStore;
import com.hf.lwdatastore.exception.CollectionNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author pldorrell
 */
public class BudgetDAOImpl extends LWMDSDAO implements BudgetDAO {

    private static final Logger log = LogManager.getLogger(BudgetDAOImpl.class);


    @Override
    public List<Budget> getBudgets() {
        ArrayList<Budget> al = new ArrayList<>();
        try {
            List<CollectionObject> cObjects = getDataStore().getObjects(ConfigBuilder.COLLECTION_BUDGET);
            if (cObjects != null && cObjects.size() > 0) {
                BudgetConverter bConverter = new BudgetConverter();
                for (CollectionObject collectionObject: cObjects) {
                    al.add(bConverter.convertFromCollectionObject(collectionObject));
                }
            }
            return al;
        }catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return null;            
    }

    @Override
    public Budget getById(String _id) {
        try {
            CollectionObject cObject = getDataStore().getObject(ConfigBuilder.COLLECTION_BUDGET, _id);
            if (cObject != null) {
                return (new BudgetConverter()).convertFromCollectionObject(cObject);
            }
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);
        }
        return null;
    }

    @Override
    public Budget createBudget(Budget _budget) {
        Budget budget = this.saveBudget(_budget);
        return budget;
    }

    @Override
    public void deleteBudget(Budget budget) {
        try {
            getDataStore().removeObject(ConfigBuilder.COLLECTION_BUDGET, budget.getId());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);
        }
    }

    @Override
    public Budget saveBudget(Budget _budget) {
        try {
            getDataStore().putObject(ConfigBuilder.COLLECTION_BUDGET, new CollectionObject(_budget), new BudgetConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found", ex);
        }
        return _budget;
    }

}
