/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.files;

/**
 *
 * @author pldorrell
 */
public class ExportOptions {
    private boolean includeAccounts;
    private boolean includeTransactions;
    private boolean includeSchedule;
    private boolean includeBudget;
    private boolean includeSettings;
    
    public boolean isIncludeAccounts() {
        return includeAccounts;
    }

    public void setIncludeAccounts(boolean includeAccounts) {
        this.includeAccounts = includeAccounts;
    }

    public boolean isIncludeTransactions() {
        return includeTransactions;
    }

    public void setIncludeTransactions(boolean includeTransactions) {
        this.includeTransactions = includeTransactions;
    }

    public boolean isIncludeSchedule() {
        return includeSchedule;
    }

    public void setIncludeSchedule(boolean includeSchedule) {
        this.includeSchedule = includeSchedule;
    }

    public boolean isIncludeBudget() {
        return includeBudget;
    }

    public void setIncludeBudget(boolean includeBudget) {
        this.includeBudget = includeBudget;
    }

    public boolean isIncludeSettings() {
        return includeSettings;
    }

    public void setIncludeSettings(boolean includeSettings) {
        this.includeSettings = includeSettings;
    }
    
}
