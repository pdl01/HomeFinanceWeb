/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.reports;

import java.util.Comparator;

/**
 *
 * @author pldorrell
 */
public class ReportDataPointsNameDescendingComparator implements Comparator<ReportDataPoint>{

    @Override
    public int compare(ReportDataPoint o1, ReportDataPoint o2) {
        return (o1.getName().compareTo(o2.getName())) * -1;
    }
    
}
