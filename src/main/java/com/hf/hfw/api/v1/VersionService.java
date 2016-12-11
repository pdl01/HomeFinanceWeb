/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import com.hf.hfw.license.LicensedModule;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author pldor
 */
public interface VersionService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/modules")    
    public List<LicensedModule> getVersionedModules();
  
}
