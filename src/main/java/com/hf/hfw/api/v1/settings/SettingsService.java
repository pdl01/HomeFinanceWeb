/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1.settings;

import com.hf.homefinanceshared.Budget;
import java.util.List;
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

}
