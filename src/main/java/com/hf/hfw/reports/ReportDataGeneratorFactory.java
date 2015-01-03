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
    public ReportGenerator get(String reportType) {
        if (reportType == null) {
            return null;
        } else if (reportType.toLowerCase().equals("income")) {
            return (ReportGenerator)ApplicationState.getApplicationState().getCtx().getBean("incomeReportGenerator");
        } else {
            return null;
        }
        
    }
}