
package com.hf.hfw.license;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        String[] modulesArray = modules.toArray(new String[0]);
        return modulesArray;
    }
    public List<LicensedModule> getLicensedModules() {
        ArrayList<LicensedModule> modules = new ArrayList<>();
        for (String module:this.getControlledModules()) {
            LicensedModule licensedModule = this.getLicensedModule(module);
            if (licensedModule != null) {
                modules.add(licensedModule);
            }
        }
        return modules;
    }

    public LicensedModule getLicensedModule(String module) {
        LicensedModule licensedModule = new LicensedModule();
        licensedModule.setName(module);
        Version version = new Version();
        version.setMajorVersion("1");
        version.setMinorVersion("0.0");
        licensedModule.setVersion(version);
        licensedModule.setValidFrom("1969-12-31");
        licensedModule.setValidUntil("2020-12-31");
        return licensedModule;
    }    

    @Override
    public boolean isLicensed(String module) {
        
    
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
