/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.reports;

import com.hf.hfw.application.ApplicationState;

/**
 *
 * @author pldorrell
 */
public class ReportDataGeneratorFactory {
    //TODO externalize this somehow
    public ReportGenerator get(String reportType) {
        if (reportType == null) {
            return null;
        } else if (reportType.toLowerCase().equals("income")) {
            return (ReportGenerator)ApplicationState.getApplicationState().getCtx().getBean("incomeReportGenerator");
        } else if (reportType.toLowerCase().equals("expensebypayee")){
            return (ReportGenerator)ApplicationState.getApplicationState().getCtx().getBean("expenseByPayeeReportGenerator");

        } else if (reportType.toLowerCase().equals("expensebycategory")) {
            return (ReportGenerator)ApplicationState.getApplicationState().getCtx().getBean("expenseByCategoryReportGenerator");
        } else if (reportType.toLowerCase().equals("dailybalance")) {
            return (ReportGenerator)ApplicationState.getApplicationState().getCtx().getBean("dailyBalanceReportGenerator");
        } else {
            return null;
        }
        
    }
}
