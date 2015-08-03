/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author pldorrell
 */
public class DailyBalanceDateHelper {
    public Set<Date> getDatesToBuildFor(Date startDate,Date endDate) {
        TreeSet<Date> dates = new TreeSet<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        dates.add(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        while (calendar.before(endCalendar)){
            calendar.add(Calendar.DATE, 1);
            dates.add(calendar.getTime());
        }
        
        
        return dates;
    }
    public Date getEndDate(Date startDate,Date referenceDate) {
        return null;
    }
}
