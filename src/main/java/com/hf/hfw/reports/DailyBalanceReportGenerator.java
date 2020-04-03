/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.reports;

import com.hf.hfw.files.FileDownloadServlet;
import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.DailyBalanceManager;
import com.hf.hfw.manager.RegisterManager;
import com.hf.hfw.manager.ScheduledTransactionManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.DailyBalance;
import com.hf.homefinanceshared.ScheduledTransaction;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pldorrell
 */
public class DailyBalanceReportGenerator implements ReportGenerator{
    private static final Logger log = LogManager.getLogger(DailyBalanceReportGenerator.class);

    public static final String REPORT_TYPE = "DailyBalance";
    private AccountManager accountManager;
    private ScheduledTransactionManager scheduledTransactionManager;
    private DailyBalanceManager dailyBalanceManager; 

    public void setDailyBalanceManager(DailyBalanceManager dailyBalanceManager) {
        this.dailyBalanceManager = dailyBalanceManager;
    }
    



    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }


    public void setScheduledTransactionManager(ScheduledTransactionManager scheduledTransactionManager) {
        this.scheduledTransactionManager = scheduledTransactionManager;
    }
    
    @Override
    public ReportData generateReportData(ReportOptions reportOptions) {
        ReportData reportData = new ReportData();
        
        List<ReportDataPoint> reportDataPoints = new ArrayList<ReportDataPoint>();
        
        //get all previous
        List<DailyBalance> dailyBalances = this.dailyBalanceManager.getByAccountId(reportOptions.getAccountId());
        
        
        //add todays - current amount
        Account account = this.accountManager.getAccountById(reportOptions.getAccountId());
        DailyBalance currentDailyBalance = new DailyBalance();
        currentDailyBalance.setAccountId(account.getId());
        currentDailyBalance.setAmount(account.getCurrentBalance());
        dailyBalances.add(currentDailyBalance);
        
        double runningBalance = currentDailyBalance.getAmount();
        String runningDate = "";//currentDailyBalance.getDailyBalanceDate();
        //add schedule
        List<ScheduledTransaction> scheduledTransactions = scheduledTransactionManager.getUpcomingTransactions(account);
        DailyBalance runningDailyBalance = new DailyBalance();
        Date currentDate = new Date();
        for (ScheduledTransaction sTxn:scheduledTransactions) {
            if (!ScheduledTransaction.STATUS_PAID.equalsIgnoreCase(sTxn.getStatusTxt()) && 
                !ScheduledTransaction.STATUS_SKIPPED.equalsIgnoreCase(sTxn.getStatusTxt()) &&
                this.isDateAfterDate(currentDate,sTxn.getScheduledDate())
                    ) {
                if (!sTxn.getScheduledDate().equalsIgnoreCase(runningDate)) {
                    if (!"".equalsIgnoreCase(sTxn.getScheduledDate())) {
                        runningDailyBalance.setAmount(runningBalance);
                        dailyBalances.add(runningDailyBalance);
                    }
                    runningDailyBalance = new DailyBalance();
                    runningDailyBalance.setAccountId(account.getId());
                    runningDailyBalance.setDailyBalanceDate(sTxn.getScheduledDate());
                    if (sTxn.isCredit()) {
                        runningBalance = runningBalance + sTxn.getTxnAmount();     
                    } else {
                        runningBalance = runningBalance - sTxn.getTxnAmount();
                    }
                } else {
                    if (sTxn.isCredit()) {
                        runningBalance = runningBalance + sTxn.getTxnAmount();     
                    } else {
                        runningBalance = runningBalance - sTxn.getTxnAmount();
                    }
                }
                runningDate = sTxn.getScheduledDate();

                
                
            }    
        }
        
        //convert the dailyBalances to report data points
        for (DailyBalance dailyBalance:dailyBalances) {
            ReportDataPoint rdp = new ReportDataPoint();
            rdp.setName(dailyBalance.getDailyBalanceDate());
            rdp.setValue(dailyBalance.getAmount());
            reportDataPoints.add(rdp);
        }
        
        reportData.setReportName("DailyBalance");
        reportData.setReportType(REPORT_TYPE);
        reportData.setDataPoints(reportDataPoints);
        return reportData;
    }
    
    private boolean isDateAfterDate(Date referenceDate,String txnDateString){
        try {
            Date txnDate = scheduledTransactionManager.transactionDateFormatter.parse(txnDateString);
            if (txnDate.before(referenceDate)) {
                return false;
            }
        } catch (ParseException ex) {
            log.error("bad date:"+txnDateString);
        }
        return true;
    }
}
