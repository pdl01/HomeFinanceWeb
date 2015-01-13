/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.dao.RegisterDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author pldorrell
 */
public class CategoryCacheManagerImpl {

    private RegisterDAO registerDAO;

    public RegisterDAO getRegisterDAO() {
        return registerDAO;
    }

    public void setRegisterDAO(RegisterDAO registerDAO) {
        this.registerDAO = registerDAO;
    }

    private Set<String> categorySet;

    public void init() {
        this.initCategorySet();
        this.initDefaultCategories();
        this.initCategoriesFromDB();
    }

    private void initCategorySet() {
        this.categorySet = new TreeSet<>();
    }

    private void initDefaultCategories() {
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

    }

    private void initCategoriesFromDB() {
        try {
        if (this.registerDAO != null) {
            this.categorySet.addAll(this.registerDAO.getAllCategories());    
        }    
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
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
                }
            }
        }
        return returnList;
    }

}
