package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.Budget;
import com.hf.homefinanceshared.BudgetCategory;
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
 * @author pldorrell
 */
public interface BudgetService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/search/all")    
    public List<Budget> getBudgets();
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getbyId/{id}")    
    public Budget getById(@PathParam("id") String id);
    
    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/save")    
    public Budget saveBudget(Budget budget) throws Exception;
    
    @DELETE
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/delete/{id}")
    public void deleteBudget(@PathParam("id") String id);
    
    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/saveBudgetItem/{id}/{type}/")    
    public Budget saveBudgetItem(@PathParam("id") String id,@PathParam("type") String type,BudgetCategory budgetCategory) throws Exception;

    @DELETE
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/deleteBudgetItem/{id}/{type}/")    
    public Budget deleteBudgetItem(@PathParam("id") String id,@PathParam("type") String type,BudgetCategory budgetCategory) throws Exception;
}
