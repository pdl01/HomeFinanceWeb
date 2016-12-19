package com.hf.hfw.files;

import com.hf.hfw.accounts.tasks.AccountBalanceNotifierTask;
import com.hf.hfw.api.v1.settings.SettingsBean;
import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.application.ConfigurationDirectoryService;
import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.BudgetManager;
import com.hf.hfw.manager.RegisterManager;
import com.hf.hfw.manager.SettingsManager;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.Budget;
import com.hf.homefinanceshared.RegisterTransaction;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author pldor
 */
public class DataFileLoader {

    private static final Logger log = Logger.getLogger(DataFileLoader.class);

    protected AccountManager accountManager;
    protected BudgetManager budgetManager;
    protected SettingsManager settingsManager;
    protected RegisterManager registerManager;

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public BudgetManager getBudgetManager() {
        return budgetManager;
    }

    public void setBudgetManager(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }

    public void performInitialLoad() {
        //this.loadSettingsFile();
        //this.loadAccountsFile();
        //this.loadBudgetsFile();
//        this.loadRegistryFiles();
    }

    public void performSave() {
        //this.saveSettingsFile();
        //this.saveAccountsFile();
        //this.saveBudgetsFile();
//        this.saveRegistryFiles();

    }

    public void loadAccountsFile() {
        /*
        ConfigurationDirectoryService cds = ApplicationState.getApplicationState().getConfigurationDirectoryService();

        File accountFile = new File(cds.getDataStorageDirectory() + File.separator + "accounts.data");
        if (accountFile.exists() && accountFile.canRead()) {
            DataFile<Account> accountDataFile = new DataFile<>();
            accountDataFile.loadFromFile(accountFile, Account.class);
            List<Account> accounts = accountDataFile.getObjectData();
            if (accounts != null) {
                for (Account account : accounts) {
                    this.accountManager.addAccountToSystem(account);
                }
            }

        }
        */
    }

    public void loadBudgetsFile() {
        ConfigurationDirectoryService cds = ApplicationState.getApplicationState().getConfigurationDirectoryService();

        File budgetsFile = new File(cds.getDataStorageDirectory() + File.separator + "budgets.data");
        if (budgetsFile.exists() && budgetsFile.canRead()) {
            DataFile<Budget> budgetsDataFile = new DataFile<>();
            budgetsDataFile.loadFromFile(budgetsFile, Budget.class);
            List<Budget> budgets = budgetsDataFile.getObjectData();
            if (budgets != null) {
                for (Budget budget : budgets) {
                    this.budgetManager.addBudgetToSystem(budget);
                }
            }

        }
    }

    public void loadSettingsFile() {
        /*
        ConfigurationDirectoryService cds = ApplicationState.getApplicationState().getConfigurationDirectoryService();

        File settingsFile = new File(cds.getDataStorageDirectory() + File.separator + "settings.data");
        if (settingsFile.exists() && settingsFile.canRead()) {
            DataFile<SettingsBean> settingsDataFile = new DataFile<>();
            settingsDataFile.loadFromFile(settingsFile, SettingsBean.class);
            List<SettingsBean> settings = settingsDataFile.getObjectData();
            if (settings != null) {
                for (SettingsBean settingsBean : settings) {
                    this.settingsManager.addSettingsBeanToSystem(settingsBean);
                }
            }

        }
        */
    }

    public void saveSettingsFile() {
        /*
        ConfigurationDirectoryService cds = ApplicationState.getApplicationState().getConfigurationDirectoryService();

        File settingsFile = new File(cds.getDataStorageDirectory() + File.separator + "settings.data");
        DataFile<SettingsBean> settingsDataFile = new DataFile<>();
        settingsDataFile.setDescriptor("settings");
        settingsDataFile.setVersion(ApplicationState.getApplicationState().getCurrentVersion());
        settingsDataFile.setObjectData(this.settingsManager.getAllSettings());
        settingsDataFile.writeToFile(settingsFile, SettingsBean.class);
        */
    }

    public void saveAccountsFile() {
        /*
        ConfigurationDirectoryService cds = ApplicationState.getApplicationState().getConfigurationDirectoryService();

        File accountsFile = new File(cds.getDataStorageDirectory() + File.separator + "accounts.data");
        DataFile<Account> accountsDataFile = new DataFile<>();
        accountsDataFile.setDescriptor("account");
        accountsDataFile.setVersion(ApplicationState.getApplicationState().getCurrentVersion());
        accountsDataFile.setObjectData(this.accountManager.getAccounts());
        accountsDataFile.writeToFile(accountsFile, Account.class);
        */
}

    public void saveBudgetsFile() {
        ConfigurationDirectoryService cds = ApplicationState.getApplicationState().getConfigurationDirectoryService();

        File budgetsFile = new File(cds.getDataStorageDirectory() + File.separator + "budgets.data");
        DataFile<Budget> budgetsDataFile = new DataFile<>();
        budgetsDataFile.setDescriptor("budget");
        budgetsDataFile.setVersion(ApplicationState.getApplicationState().getCurrentVersion());
        budgetsDataFile.setObjectData(this.budgetManager.getBudgets());
        budgetsDataFile.writeToFile(budgetsFile, Budget.class);
    }

    public void saveRegistryFiles() {
        List<Account> accounts = this.accountManager.getAccounts();
        for (Account account:accounts) {
            this.saveRegistryFile(account);
        }
    }

    public void saveRegistryFile(Account account) {
        ConfigurationDirectoryService cds = ApplicationState.getApplicationState().getConfigurationDirectoryService();

        File registryFile = new File(cds.getDataStorageDirectory() + File.separator + "register"+account.getId()+".data");
        DataFile<RegisterTransaction> registerDataFile = new DataFile<>();
        registerDataFile.setDescriptor("registry");
        registerDataFile.setVersion(ApplicationState.getApplicationState().getCurrentVersion());
        registerDataFile.setObjectData(this.registerManager.getTransactions(account));
        registerDataFile.writeToFile(registryFile, RegisterTransaction.class);

    }
    
    public void loadRegistryFile(String _filename) {
        ConfigurationDirectoryService cds = ApplicationState.getApplicationState().getConfigurationDirectoryService();

        File registryFile = new File(cds.getDataStorageDirectory() + File.separator + _filename);
        //File registryFile = new File (_filename);
        if (registryFile.exists() && registryFile.canRead()) {
            DataFile<RegisterTransaction> registryDataFile = new DataFile<>();
            registryDataFile.loadFromFile(registryFile, RegisterTransaction.class);
            List<RegisterTransaction> transactions = registryDataFile.getObjectData();
            if (transactions != null) {
                for (RegisterTransaction txn : transactions) {
                    this.registerManager.addRegisterTransactionToSystem(txn);
                }
            }
            //trigger something after all accounts have been added

        }

    }
    
    public void loadRegistryFiles() {
        ConfigurationDirectoryService cds = ApplicationState.getApplicationState().getConfigurationDirectoryService();
        File registryFileDirectory = new File(cds.getDataStorageDirectory());
        if (registryFileDirectory.exists() && registryFileDirectory.canRead()) {
            String[] registryFiles = registryFileDirectory.list(new FilenameFilter() {
                public boolean accept(File directory, String fileName) {
                    if (fileName.startsWith("register") && fileName.endsWith(".data")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (String registerFileName:registryFiles) {
                log.info("Loading "+registerFileName);

                this.loadRegistryFile(registerFileName);
            }
            
        }
    }
}
