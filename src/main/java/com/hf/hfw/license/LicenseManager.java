
package com.hf.hfw.license;

import java.util.Date;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface LicenseManager {
    public String[] getControlledModules();
    public LicensedModule getLicensedModule(String module);
    public boolean isLicensed(String module);
    public Date getLicenseValidUntil(String module);
    public Date getLicenseValidStart(String module);
    public boolean applyLicense(String licenseString);
    public List<LicensedModule> getLicensedModules();
}
