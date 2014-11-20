package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.Account;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author phillip.dorrell
 */
public interface AccountService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/search/all")    
    public List<Account> getAccounts();
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getbyId/{accountId}")    
    public Account getById(@PathParam("accountId") String accountId);
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getbyName/{name}")    
    public Account getByName(@PathParam("name") String name);
    
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getbyExternalId/{externalId}")    
    public Account getByExternalId(@PathParam("externalId") String externalId);
    
    
    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/save")    
    public Account saveAccount(Account account) throws Exception;
    
    @DELETE
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/delete/{accountId}")
    public void deleteAccount(@PathParam("accountId") String accountId); 
    
      
}
