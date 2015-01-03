
package com.hf.hfw.api.v1;

import com.hf.hfw.manager.ReportManager;
import com.hf.hfw.reports.ReportData;
import javax.ws.rs.Path;

/**
 *
 * @author pldorrell
 */
@Path("/reports")
public class ReportServiceImpl implements ReportService {
    
    private ReportManager reportManager;

    public ReportManager getReportManager() {
        return reportManager;
    }

    public void setReportManager(ReportManager reportManager) {
        this.reportManager = reportManager;
    }
    
    @Override
    public ReportData getReport(String accountId,String reportType) {
       return this.reportManager.getReport(accountId,reportType);
    }

    @Override
    public ReportData getReport(String accountId,String reportType, String startDate, String endDate) {
        return this.reportManager.getReport(accountId,reportType, startDate, endDate);
    }

    @Override
    public ReportData getReport(String accountId,String reportType, String reportPeriod) {
       return this.reportManager.getReport(accountId,reportType,reportPeriod);
    }
    
}
