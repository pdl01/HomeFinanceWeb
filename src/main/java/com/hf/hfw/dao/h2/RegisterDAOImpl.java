package com.hf.hfw.dao.h2;

import com.hf.hfw.dao.RegisterDAO;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.OnlineTransaction;
import com.hf.homefinanceshared.RegisterTransaction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author pldor
 */
public class RegisterDAOImpl extends H2DAO implements RegisterDAO {

    @Override
    public List<RegisterTransaction> getTransactions(Account account) {
        String sql = "select * from registryTxns where primaryAccount = :primaryAccount";
        Map<String, Object> argMap = new HashMap<String, Object>();
        argMap.put("primaryAccount", account.getId());

        List<RegisterTransaction> txns = this.getNamedParameterJdbcTemplate().query(sql, argMap, new RegisterTransactionRowMapper());
        return txns;
    }

    @Override
    public List<RegisterTransaction> getTransactionsByCategories(Account account, List<String> categories) {
        return new ArrayList<RegisterTransaction>();
    }

    @Override
    public RegisterTransaction getTransactionById(String _id) {
        String sql = "select * from registryTxns where id = :id";
        Map<String, Object> argMap = new HashMap<String, Object>();
        argMap.put("id", _id);

        RegisterTransaction txn = this.getNamedParameterJdbcTemplate().queryForObject(sql, argMap, new RegisterTransactionRowMapper());
        return txn;
    }

    @Override
    public RegisterTransaction createTransaction(RegisterTransaction txn) {
        String sql = "insert into registryTxns (  id,payee,statusText,primaryAccount,secondaryAccount,txnType,txnPersonalRefNumber,txnExternalRefNumber,txnDate,createdDate,lastModifiedDate,credit,txnAmount,memo,flagged,flagComment) values (:id,:payee,:statusText,:primaryAccount,:secondaryAccount,:txnType,:txnPersonalRefNumber,:txnExternalRefNumber,:txnDate,:createdDate,:lastModifiedDate,:credit,:txnAmount,:memo,:flagged,:flagComment)";        Map<String, Object> argMap = new HashMap<String, Object>();
        argMap.put("id", txn.getId());
        argMap.put("payee", txn.getPayee());
        argMap.put("primaryAccount", txn.getPrimaryAccount());
        argMap.put("secondaryAccount", txn.getSecondaryAccount());
        argMap.put("txnType", txn.getTxnType());
        argMap.put("txnAmount", txn.getTxnAmount());
        argMap.put("txnPersonalRefNumber", txn.getTxnPersonalRefNumber());
        argMap.put("txnExternalRefNumber", txn.getTxnExternalRefNumber());
        argMap.put("txnDate", txn.getTxnDate());
        argMap.put("createdDate",txn.getCreatedDate().getTime());
        argMap.put("lastModifiedDate",txn.getLastModifiedDate().getTime());
        argMap.put("credit", txn.isCredit());
        argMap.put("flagged", txn.isFlagged());
        argMap.put("statusText",txn.getStatusTxt());
        argMap.put("memo", txn.getMemo());
        argMap.put("flagComment",txn.getFlagComment());
                
        int result = this.getNamedParameterJdbcTemplate().update(sql, argMap);
        return txn;
    }

    @Override
    public void deleteTransaction(RegisterTransaction txn) {

    }

    @Override
    public RegisterTransaction updateTransaction(RegisterTransaction txn) {
        return null;
    }

    @Override
    public List<RegisterTransaction> getTransactionsByCategoriesStartsWithForDateStartWith(Account account, String category, String date) {
        return new ArrayList<RegisterTransaction>();
    }

    @Override
    public Set<String> getAllCategories() {
        return new TreeSet<String>();
    }

    @Override
    public List<RegisterTransaction> getTransactionsForDateStartWith(Account account, String date, boolean getCredit) {
        String sql = "select * from registryTxns where primaryAccount = :primaryAccount and txnDate like :txnDateSearch";
        Map<String, Object> argMap = new HashMap<String, Object>();
        argMap.put("primaryAccount", account.getId());
        argMap.put("txnDateSearch",date+"%");

        List<RegisterTransaction> txns = this.getNamedParameterJdbcTemplate().query(sql, argMap, new RegisterTransactionRowMapper());
        return txns;
    }

    @Override
    public void addPendingTransactions(List<OnlineTransaction> txns) {

    }

    @Override
    public List<OnlineTransaction> getPendingTransactions(Account account) {
        return new ArrayList<OnlineTransaction>();
    }

    @Override
    public List<RegisterTransaction> getAllTransactionsForDateStartWith(Account account, String date) {
        String sql = "select * from registryTxns where primaryAccount = :primaryAccount and txnDate like :txnDateSearch";
        Map<String, Object> argMap = new HashMap<String, Object>();
        argMap.put("primaryAccount", account.getId());
        argMap.put("txnDateSearch",date+"%");

        List<RegisterTransaction> txns = this.getNamedParameterJdbcTemplate().query(sql, argMap, new RegisterTransactionRowMapper());
        return txns;
    }

    @Override
    public OnlineTransaction getPendingTransactionById(String id) {
        return null;
    }

    @Override
    public List<RegisterTransaction> matchTransaction(OnlineTransaction pendingTransaction) {
        return new ArrayList<RegisterTransaction>();
    }

    @Override
    public List<RegisterTransaction> findTransaction(String searchTerm) {
        return new ArrayList<RegisterTransaction>();
    }

    @Override
    public List<RegisterTransaction> findTransaction(Account account, String searchTerm) {
        return new ArrayList<RegisterTransaction>();
    }

    @Override
    public Set<String> getAllNames() {
        return new TreeSet<String>();
    }

    @Override
    public List<RegisterTransaction> getAllTransactions() {
        String sql = "select * from registryTxns";
        Map<String, Object> argMap = new HashMap<String, Object>();
        //argMap.put("primaryAccount", account.getId());

        List<RegisterTransaction> txns = this.getNamedParameterJdbcTemplate().query(sql,  new RegisterTransactionRowMapper());
        return txns;
    }

    class RegisterTransactionRowMapper implements org.springframework.jdbc.core.RowMapper<RegisterTransaction> {

        @Override
        public RegisterTransaction mapRow(ResultSet rs, int i) throws SQLException {
            RegisterTransaction rt = new RegisterTransaction();
            rt.setId(rs.getString("id"));
            rt.setPayee(rs.getString("payee"));
            rt.setTxnAmount(rs.getDouble("txnAmount"));
            rt.setPrimaryAccount(rs.getString("primaryAccount"));
            rt.setSecondaryAccount(rs.getString("secondaryAccount"));
            rt.setTxnDate(rs.getString("txnDate"));
            Date createdDate = new Date(rs.getLong("createdDate"));
            rt.setCreatedDate(createdDate);
            Date modifiedDate = new Date(rs.getLong("lastModifiedDate"));
            rt.setLastModifiedDate(modifiedDate);
            rt.setMemo(rs.getString("memo"));

            //TODO do rest of data
            return rt;
        }

    }
}
