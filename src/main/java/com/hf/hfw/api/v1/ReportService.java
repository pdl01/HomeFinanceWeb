/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import com.hf.hfw.reports.ReportData;
import com.hf.homefinanceshared.Account;
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
public interface ReportService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/{accountId}/{reportType}")    
    public ReportData getReport(@PathParam("accountId") String accountId,@PathParam("reportType") String reportType);

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/{accountId}/{reportType}/{reportPeriod}")    
    public ReportData getReport(@PathParam("accountId") String accountId,@PathParam("reportType") String reportType,@PathParam("reportPeriod") String reportPeriod);

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/{accountId}/{reportType}/{startDate}/{endDate}")    
    public ReportData getReport(@PathParam("accountId") String accountId,@PathParam("reportType") String reportType,@PathParam("startDate") String startDate,@PathParam("endDate") String endDate);

}
