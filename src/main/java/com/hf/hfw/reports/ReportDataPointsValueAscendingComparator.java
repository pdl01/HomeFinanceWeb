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
public class ReportDataPointsValueAscendingComparator implements Comparator<ReportDataPoint>{

    @Override
    public int compare(ReportDataPoint o1, ReportDataPoint o2) {
        if (o1.getValue() > o2.getValue()) {
            return 1;
        } else if (o1.getValue() < o2.getValue() ) {
            return -1;
        } else {
            return 0;
        }
    }
    
}
