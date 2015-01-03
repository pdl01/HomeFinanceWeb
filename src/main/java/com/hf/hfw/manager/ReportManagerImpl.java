/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.reports.ReportData;
import com.hf.hfw.reports.ReportDataGeneratorFactory;
import com.hf.hfw.reports.ReportGenerator;
import com.hf.hfw.reports.ReportOptions;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author pldorrell
 */
public class ReportManagerImpl implements ReportManager {

    private ReportDataGeneratorFactory reportDataGeneratorFactory;

    public static final SimpleDateFormat sdf_year_month = new SimpleDateFormat("yyyy-MM-");
    public static final SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy-");

    public ReportDataGeneratorFactory getReportDataGeneratorFactory() {
        return reportDataGeneratorFactory;
    }

    public void setReportDataGeneratorFactory(ReportDataGeneratorFactory reportDataGeneratorFactory) {
        this.reportDataGeneratorFactory = reportDataGeneratorFactory;
    }


    @Override
    public ReportData getReport(String accountId,String reportType) {
        ReportOptions options = this.buildReportOptions(accountId, reportType, ReportOptions.REPORT_PERIOD_CURRENT_MONTH);
        return this.getReport(options);
    }

    @Override
    public ReportData getReport(ReportOptions options) {
        ReportGenerator gen = this.reportDataGeneratorFactory.get(options.getReportType());
        return gen.generateReportData(options);
    }
    
    @Override
    public ReportData getReport(String accountId,String reportType, String reportPeriod) {
        ReportOptions options = this.buildReportOptions(accountId, reportType, reportPeriod);
        return this.getReport(options);
        
        
        //return this.getReport(accountId,reportType, beginDate, endDate);
    }

    @Override
    public ReportData getReport(String accountId,String reportType, String beginDate, String endDate) {
        //ReportGenerator gen = this.reportDataGeneratorFactory.get(reportType);
        ReportOptions options = this.buildReportOptions(accountId, reportType, "CUSTOM");
        options.setBeginDate(beginDate);
        options.setEndDate(endDate);
        return this.getReport(options);
        
    }

    private ReportOptions buildReportOptions (String accountId,String reportType,String reportPeriod){
        ReportOptions options = new ReportOptions();
        options.setAccountId(accountId);
        options.setReportPeriod(reportPeriod);
        options.setReportType(reportType);
        return options;
    }
}
