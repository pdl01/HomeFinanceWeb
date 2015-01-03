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
        this.initCategoriesFromDB();
    }
    private void initCategorySet() {
        this.categorySet = new TreeSet<>();
    }
    private void initCategoriesFromDB() {
        this.categorySet.addAll(this.registerDAO.getAllCategories());
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
