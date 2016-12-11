package com.hf.hfw.reports;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public class ReportOptions {
    public static final SimpleDateFormat sdf_year_month = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");

    
    public static final String REPORT_PERIOD_CURRENT_MONTH = "currentMonth";
    public static final String REPORT_PERIOD_PREVIOUS_MONTH = "previousMonth";

    public static final String REPORT_PERIOD_CURRENT_YEAR = "currentYear";

    public static final String REPORT_PERIOD_PREVIOUS_YEAR = "previousYear";

    
    private String accountId;
    private List<String> accounts;
    private String beginDate;
    private String endDate;
    private String reportType;
    private String reportPeriod;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    
    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportPeriod() {
        return reportPeriod;
    }

    public void setReportPeriod(String reportPeriod) {
        this.reportPeriod = reportPeriod;
    }
    
    public String getDateQueryStringBasedOnPeriod() {
        
        Date currentDate = new Date();
        if (reportPeriod == null) {
            return null;
                } else if (reportPeriod.equals(REPORT_PERIOD_CURRENT_MONTH)) {
            return sdf_year_month.format(currentDate);
            //endDate = sdf_year_month.format(currentDate)+"-31";

        } else if (reportPeriod.equals(REPORT_PERIOD_PREVIOUS_MONTH)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.MONTH, -1);
            return sdf_year_month.format(cal.getTime());
            //endDate = sdf_year_month.format(cal.getTime());

        } else if (reportPeriod.equals(REPORT_PERIOD_PREVIOUS_YEAR)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.YEAR, -1);

            return sdf_year.format(cal.getTime());
            //endDate = sdf_year.format(cal.getTime())+"-12-";

        } else if (reportPeriod.equals(REPORT_PERIOD_CURRENT_YEAR)) {
            return sdf_year.format(currentDate);
            //endDate = sdf_year.format(currentDate)+"-12-31";
        } else {
            return null;
        }
    }
/*
    private void setupDatesBasedOnPeriod() {
        String beginDate = "";
        String endDate = "";
        Date currentDate = new Date();
        if (reportPeriod == REPORT_PERIOD_CURRENT_MONTH) {
            beginDate = sdf_year_month.format(currentDate);
            //endDate = sdf_year_month.format(currentDate)+"-31";

        } else if (reportPeriod == REPORT_PERIOD_PREVIOUS_MONTH) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.MONTH, -1);
            beginDate = sdf_year_month.format(cal.getTime());
            //endDate = sdf_year_month.format(cal.getTime());

        } else if (reportPeriod == REPORT_PERIOD_CURRENT_YEAR) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.YEAR, -1);

            beginDate = sdf_year.format(cal.getTime());
            //endDate = sdf_year.format(cal.getTime())+"-12-";

        } else if (reportPeriod == REPORT_PERIOD_PREVIOUS_YEAR) {
            beginDate = sdf_year.format(currentDate);
            //endDate = sdf_year.format(currentDate)+"-12-31";
        }
    }
  */  
}
