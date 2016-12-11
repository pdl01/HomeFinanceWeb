/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import com.hf.hfw.license.LicenseManager;
import com.hf.hfw.license.LicensedModule;
import java.util.List;
import javax.ws.rs.Path;

/**
 *
 * @author pldor
 */
@Path("/versions")
public class VersionServiceImpl implements VersionService{
    protected LicenseManager licenseManager;

    public void setLicenseManager(LicenseManager licenseManager) {
        this.licenseManager = licenseManager;
    }
    
    @Override
    public List<LicensedModule> getVersionedModules() {
        return this.licenseManager.getLicensedModules();
    }
    
}
