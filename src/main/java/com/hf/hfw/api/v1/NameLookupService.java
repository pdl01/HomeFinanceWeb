/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import java.util.Collection;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author pldorrell
 */
public interface NameLookupService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/lookup/{name}")    
    public List<String> lookupNames(@PathParam("name") String name);

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/all")    
    public Collection<String> getAll();
    
}
