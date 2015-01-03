package com.hf.hfw.reports;

import java.util.List;

/**
 *
 * @author pldorrell
 */
public class ReportDataPoint {
    private String name;
    private double value;
    
    private List<String> transactions;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
    
    
    
    
}
