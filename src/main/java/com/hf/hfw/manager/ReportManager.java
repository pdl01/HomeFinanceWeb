/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.reports.ReportData;
import com.hf.hfw.reports.ReportOptions;
import javax.ws.rs.PathParam;

/**
 *
 * @author pldorrell
 */
public interface ReportManager {
    public ReportData getReport(String accountId,String reportType);
    public ReportData getReport(String accountId,String reportType,String reportPeriod);
    public ReportData getReport(String accountId,String reportType,String beginDate,String endDate);
    public ReportData getReport(ReportOptions options);

}
