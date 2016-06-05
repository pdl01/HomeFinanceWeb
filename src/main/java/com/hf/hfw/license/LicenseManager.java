
package com.hf.hfw.license;

import java.util.Date;

/**
 *
 * @author pldorrell
 */
public interface LicenseManager {
    public String[] getControlledModules();
    public boolean isLicensed(String module);
    public Date getLicenseValidUntil(String module);
    public Date getLicenseValidStart(String module);
    public boolean applyLicense(String licenseString);
}
