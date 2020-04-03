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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author pldorrell
 */
public class NameCacheManagerImpl {
    private static final Logger log = LogManager.getLogger(NameCacheManagerImpl.class);

    private RegisterDAO registerDAO;
    private Set<String> names;

    public RegisterDAO getRegisterDAO() {
        return registerDAO;
    }

    public void setRegisterDAO(RegisterDAO registerDAO) {
        this.registerDAO = registerDAO;
    }

    public Set<String> getNames() {
        return names;
    }

    
    public void init() {
        log.debug("Entering init");
        this.initNamesFromDB();
        log.debug("Exiting init");

    }
    private void initNamesFromDB() {
        log.debug("Entering initCategoryFromDB");
        try {
            if (this.registerDAO != null) {
                this.names.addAll(this.registerDAO.getAllNames());
            }
        } catch (Exception e) {
            log.error(e);
        }
        log.debug("Exiting initCategoryFromDB");

    }
    public void add(String name) {
        if (this.names != null) {
            this.names.add(name);
        }
        
    }

    public void remove(String name) {
        this.names.remove(name);
    }

    public List<String> get(String name, boolean exactSearch) {
        List<String> returnList = new ArrayList<String>();
        if (exactSearch && this.names.contains(name)) {
            returnList.add(name);
        } else {
            for (String s : this.names) {
                if (s.startsWith(name)) {
                    returnList.add(s);
                }
            }
        }
        return returnList;
    }
}
