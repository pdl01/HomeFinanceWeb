
package com.hf.hfw.dao.lwmds;

import com.hf.hfw.dao.DailyBalanceDAO;
import com.hf.homefinanceshared.DailyBalance;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public class DailyBalanceDAOImpl extends LWMDSDAO implements DailyBalanceDAO{

    @Override
    public List<DailyBalance> getByAccountId(String accountId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DailyBalance> getByAccountIdForDate(String accountId, String date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateAccount(DailyBalance db) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAllForAccount(String accountId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
