/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.reports;

import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.RegisterManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.CategorySplit;
import com.hf.homefinanceshared.RegisterTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author pldorrell
 */
public class IncomeReportGenerator implements ReportGenerator {

    public static final String REPORT_TYPE = "INCOME";
    private RegisterManager registerManager;
    private AccountManager accountManager;

    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public ReportData generateReportData(ReportOptions reportOptions) {
        //load all accounts
        List<Account> accounts = new ArrayList<Account>();
        if (reportOptions.getAccountId() != null) {
            Account x = this.accountManager.getAccountById(reportOptions.getAccountId());
            accounts.add(x);
        } else if (reportOptions.getAccounts() != null) {
            for (String x : reportOptions.getAccounts()) {
                accounts.add(this.accountManager.getAccountById(x));
            }
        } else {
            accounts = this.accountManager.getAccounts();
        }
        Map<String, ReportDataPoint> data = new HashMap<String, ReportDataPoint>();
        //get all transactions that have a classification of income
        String categoryStarter = "Income";
        for (Account account : accounts) {
            //List<RegisterTransaction> txns = this.registerManager.getTransactionsByCategories(account, categories);

            //List<RegisterTransaction> txns = this.registerManager.getTransactionsByCategoriesStartsWithForDateStartWith(account,"Income",reportOptions.getDateQueryStringBasedOnPeriod());
            List<RegisterTransaction> txns = this.registerManager.getTransactionsForDateStartWith(account, reportOptions.getDateQueryStringBasedOnPeriod(), true);
            for (RegisterTransaction txn : txns) {
                if (txn.isVoid()) {
                    continue;
                }
                if (txn.getCategorySplits() != null) {
                    double categorySum = 0;
                    //make sure the data is right for the report;
                    for (CategorySplit category : txn.getCategorySplits()) {
                        if (category.getCategory().startsWith(categoryStarter)) {
                            categorySum = categorySum + category.getTxnAmount();
                            this.addToCategory(category.getCategory(), data, txn, category.getTxnAmount());
                        }

                    }
                    if (categorySum < txn.getTxnAmount()) {
                        this.addToUncategorized(data, txn, txn.getTxnAmount() - categorySum);
                    }
                } else {
                    this.addToUncategorized(data, txn, txn.getTxnAmount());
                }
            }
        }

        //pull the map in to a list
        List<ReportDataPoint> reportDataPoints = new ArrayList<ReportDataPoint>();
        for (Map.Entry<String, ReportDataPoint> entry : data.entrySet()) {
            reportDataPoints.add(entry.getValue());
        }

        ReportData reportData = new ReportData();
        reportData.setReportName("Income");
        reportData.setReportType(REPORT_TYPE);
        reportData.setDisplay("Income for xxx");
        reportData.setDataPoints(reportDataPoints);
        return reportData;
    }

    private void addToCategory(String _category,Map<String, ReportDataPoint> data, RegisterTransaction txn, double _value) {
        ReportDataPoint rdp = data.get(_category);
        List<String> x = null;
        if (rdp == null) {
            rdp = new ReportDataPoint();
            rdp.setName(_category);
            x = new ArrayList<String>();
            rdp.setTransactions(x);
        }

        double value = rdp.getValue() + _value;
        //rdp.setValue(value);

        x = rdp.getTransactions();
        x.add(txn.getId());
        rdp.setTransactions(x);
        data.put(_category, rdp);
        
    }
    private void addToUncategorized(Map<String, ReportDataPoint> data, RegisterTransaction txn, double _value) {
        this.addToCategory(ReportGenerator.UNCATEGORIZED,data, txn, _value);

    }

}
