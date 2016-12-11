/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.Account;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author pldorrell
 */
public interface DateService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/current/year")
    public String getCurrentYear();
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/current/month")    
    public String getCurrentMonth();

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/current/day")    
    public String getCurrentDay();

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/current")    
    public Object getCurrent();
}
