
package com.hf.hfw.license;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author pldorrell
 */
public class LicenseManagerImpl implements LicenseManager {

    @Override
    public String[] getControlledModules() {
        ArrayList<String> modules = new ArrayList<>();
        modules.add("Application");
        modules.add("Reports");
        modules.add("Online Imports");
        modules.add("Budget");
        modules.add("Long Term Planning");
        String[] modulesArray = modules.toArray(new String[0]);
        return modulesArray;
    }

    @Override
    public boolean isLicensed(String module) {
        //
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getLicenseValidUntil(String module) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getLicenseValidStart(String module) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean applyLicense(String licenseString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
