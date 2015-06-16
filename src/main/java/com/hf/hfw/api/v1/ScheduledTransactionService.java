/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.ScheduledTransaction;
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
public interface ScheduledTransactionService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/upcoming/{accountId}")
    public List<ScheduledTransaction> getUpcomingTransactions(@PathParam("accountId") String accountId);

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/upcoming/{accountId}/{timePeriod}")
    public List<ScheduledTransaction> getUpcomingTransactions(@PathParam("accountId") String accountId,@PathParam("timePeriod") String theTimePeriod);

    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/scheduled/{accountId}")
    public List<ScheduledTransaction> getScheduledTransactions(@PathParam("accountId") String accountId);    
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/upcoming")    
    public List<ScheduledTransaction> getUpcomingTransactions();
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/byid/{txnId}")    
    public ScheduledTransaction getById(@PathParam("txnId") String txnId);

    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/save")    
    public ScheduledTransaction saveTransaction(ScheduledTransaction transaction) throws Exception;
    
    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/skip/{txnId}")    
    public void skipTransaction(@PathParam("txnId") String transactionId) throws Exception;

    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/pay/{txnId}")    
    public void payTransaction(@PathParam("txnId") String transactionId) throws Exception;

    
    @DELETE
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/delete/{transactionId}")
    public void deleteTransaction(@PathParam("transactionId") String transactionId); 
    
    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/validate")    
    public ValidationResponse validateTransaction(ScheduledTransaction transaction) throws Exception;


    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/rebuildOriginals")    
    public ValidationResponse rebuildOriginals(ScheduledTransaction transaction) throws Exception;

    
    
}
