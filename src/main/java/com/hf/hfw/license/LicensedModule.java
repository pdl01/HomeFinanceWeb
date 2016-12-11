package com.hf.hfw.license;

/**
 *
 * @author pldorrell
 */
public class LicensedModule extends Module {

    protected String validUntil;
    protected String validFrom;

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }
    
}
