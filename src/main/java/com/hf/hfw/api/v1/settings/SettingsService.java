package com.hf.hfw.api.v1.settings;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author pldorrell
 */
public interface SettingsService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/{type}")    
    public SettingsBean getSettings(@PathParam("type") String typeOfSettings);

    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/{type}")    
    public SettingsBean saveSettings(@PathParam("type") String typeOfSettings, SettingsBean settingsbean);

    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/email/validate")    
    public String validateEmailSettings(SettingsBean settingsBean);

    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/email/test/{email}")    
    public String sendTestEmail(@PathParam("email") String email);

    
}
