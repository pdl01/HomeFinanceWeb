/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.reports;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public class ReportData {
    protected String reportType;
    protected String display;
    protected String reportName;
    protected List<ReportDataPoint> dataPoints;
    

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public List<ReportDataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<ReportDataPoint> dataPoints) {
        if (dataPoints == null || (dataPoints != null && dataPoints.isEmpty())){
            this.dataPoints = new ArrayList<ReportDataPoint>();
            ReportDataPoint rdp = new ReportDataPoint();
            rdp.setName("0");
            rdp.setValue(0);
            this.dataPoints.add(rdp);
        } else {
        this.dataPoints = dataPoints;    
        }
        
    }
    

}
