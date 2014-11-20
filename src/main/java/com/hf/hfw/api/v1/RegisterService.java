package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.RegisterTransaction;
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
public interface RegisterService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/all/{accountId}")    
    public List<RegisterTransaction> getTransactions(@PathParam("accountId") String accountId);
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getTransactionById/{transactionId}")    
    public RegisterTransaction getTransactionById(@PathParam("transactionId") String transactionId);
    
    
    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/save")    
    public RegisterTransaction saveTransaction(RegisterTransaction transaction) throws Exception;
    
    @DELETE
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/delete/{transactionId}")
    public void deleteTransaction(@PathParam("transactionId") String transactionId); 
    
      
}
