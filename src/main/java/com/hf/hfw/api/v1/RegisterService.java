package com.hf.hfw.api.v1;

import com.hf.homefinanceshared.RegisterTransaction;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    @Path("/get/{accountId}/{start}/{number}")    
    public List<RegisterTransaction> getTransactions(@PathParam("accountId") String accountId,@PathParam("number") String number,@PathParam("start") String start);

        @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/bymonth/{accountId}/{month}")    
    public List<RegisterTransaction> getTransactionsByMonth(@PathParam("accountId") String accountId,@PathParam("month") String month);

        @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/bydate/{accountId}/{date}")    
    public List<RegisterTransaction> getTransactionsByDate(@PathParam("accountId") String accountId,@PathParam("date") String date);

    
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
    
    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/validate")    
    public ValidationResponse validateTransaction(RegisterTransaction transaction) throws Exception;

    
    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/upload/{accountId}")    
    public void uploadFile(@PathParam("accountId") String accountId,@FormParam("data") String data);

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/pending/{accountId}")    
    public List<RegisterTransaction> getPendingTransactions(@PathParam("accountId") String accountId);
  
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/pending/{accountId}/{number}/{start}")    
    public List<RegisterTransaction> getPendingTransactions(@PathParam("accountId") String accountId,@PathParam("number") String number,@PathParam("start") String start);
 
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/get/matched/{transactionid}")    
    public List<RegisterTransaction> getMatchedTransactionsForPending(@PathParam("transactionid") String transactionid);

    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/pending/match/{pendingTransactionid}/{enteredTransactionId}")    
    public ValidationResponse matchTransaction(@PathParam("pendingTransactionid") String pendingTransactionid,@PathParam("enteredTransactionId") String enteredTransactionId);

    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/pending/dismiss/{pendingTransactionid}")    
    public ValidationResponse dismissPendingTransaction(@PathParam("pendingTransactionid") String pendingTransactionid);

    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/pending/acceptasnew/{pendingTransactionid}")    
    public RegisterTransaction acceptPendingTransactionAsNew(@PathParam("pendingTransactionid") String pendingTransactionid);
}
