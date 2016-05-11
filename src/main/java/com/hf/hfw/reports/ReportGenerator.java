/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.reports;

/**
 *
 * @author pldorrell
 */
public interface ReportGenerator {
    public static final String UNCATEGORIZED = "Uncategorized";
    public ReportData generateReportData(ReportOptions reportOptions);
     
}
