package com.hf.hfw.manager;

import com.hf.hfw.dao.RegisterDAO;
import com.hf.homefinanceshared.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author pldorrell
 */
public class CategoryCacheManagerImpl {
    public static String TRANSFER_FROM = "Transfer:From:";
    public static String TRANSFER_TO = "Transfer:To:";
    
    private static final Logger log = LogManager.getLogger(CategoryCacheManagerImpl.class);

    private RegisterDAO registerDAO;
    private AccountManager accountManager;

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
    
    public RegisterDAO getRegisterDAO() {
        return registerDAO;
    }

    public void setRegisterDAO(RegisterDAO registerDAO) {
        this.registerDAO = registerDAO;
    }

    private Set<String> categorySet;

    public Set<String> getCategorySet() {
        return categorySet;
    }

    public void init() {
        log.debug("Entering init");
        this.initCategorySet();
    
        this.initDefaultCategories();
        try {
            this.initAccountTransferCategorySet();    
            this.initCategoriesFromDB();    
        } catch (Exception e) {
            log.warn("Unable to populate categories from DB",e);
        }
        
        log.debug("Exiting init");

    }
    private void initAccountTransferCategorySet(){
        List<Account> accounts = this.accountManager.getAccounts();
        for (Account account: accounts) {
            this.categorySet.add(TRANSFER_TO+account.getName());
            this.categorySet.add(TRANSFER_FROM+account.getName());
        }
    }
    private void initCategorySet() {
        log.debug("Entering initCategorySet");

        this.categorySet = new TreeSet<String>();
        log.debug("Exiting initCategorySet");

    }

    private void initDefaultCategories() {
        log.debug("Entering initDefaultCategories");

        //TODO:externalize this into a file
        this.categorySet.add("Income");
        this.categorySet.add("Income:Salary:Net Pay");
        this.categorySet.add("Income:Salary:Bonus Pay");
        this.categorySet.add("Income:Gifts Received");

        this.categorySet.add("Utilities:Electricity");
        this.categorySet.add("Utilities:Water");
        this.categorySet.add("Utilities:Sewer");
        this.categorySet.add("Utilities:Gas");
        this.categorySet.add("Automobile:Fuel");
        this.categorySet.add("Automobile:Service:Routine");
        this.categorySet.add("Automobile:Service:Repair");

        this.categorySet.add("Food:Groceries");
        this.categorySet.add("Food:Dining");

        this.categorySet.add("Taxes:Federal Income Tax");
        this.categorySet.add("Taxes:State Income Tax");
        log.debug("Exiting initDefaultCategories");

    }

    private void initCategoriesFromDB() {
        log.debug("Entering initCategoryFromDB");
        try {
            if (this.registerDAO != null) {
                this.categorySet.addAll(this.registerDAO.getAllCategories());
            }
        } catch (Exception e) {
            log.error(e);
        }
        log.debug("Exiting initCategoryFromDB");

    }

    public void add(String category) {
        if (this.categorySet == null) {
        }
        this.categorySet.add(category);
    }

    public void remove(String category) {
        this.categorySet.remove(category);
    }

    public List<String> get(String category, boolean exactSearch) {
        List<String> returnList = new ArrayList<String>();
        if (exactSearch && this.categorySet.contains(category)) {
            returnList.add(category);
        } else {
            for (String s : this.categorySet) {
                if (s.startsWith(category)) {
                    returnList.add(s);
                } else if (s.startsWith("Expenses:"+category) || s.startsWith("Income:"+category)) {
                    returnList.add(s);
                }
            }
        }
        return returnList;
    }
}
