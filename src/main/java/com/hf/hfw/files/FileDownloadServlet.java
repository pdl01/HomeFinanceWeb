package com.hf.hfw.files;

import com.hf.hfw.manager.AccountManager;
import com.hf.hfw.manager.BudgetManager;
import com.hf.hfw.manager.RegisterManager;
import com.hf.hfw.manager.ScheduledTransactionManager;
import com.hf.homefinanceshared.Account;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author pldorrell
 */
@WebServlet(name = "FileDownloadServlet", urlPatterns = {"/filedownload"})
public class FileDownloadServlet extends HttpServlet {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FileDownloadServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.buildExport(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        if ("exportsettings".equalsIgnoreCase(operation)) {
            this.buildExport(req, resp); //To change body of generated methods, choose Tools | Templates.
        } 

    }
    
    
    protected void buildExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "filename=\"hfwExport.json\"");
        
        //get the account to download
        //get the format
        //get the options
        ExportOptions exportOptions = new ExportOptions();
        exportOptions.setIncludeAccounts(true);
        exportOptions.setIncludeBudget(true);
        exportOptions.setIncludeSchedule(true);
        exportOptions.setIncludeTransactions(true);

        // obtains response's output stream
        //StringReader sr = new StringReader(this.buildJSONString());
        OutputStream outStream = response.getOutputStream();
        
        String text = this.buildJSONString(exportOptions);
        if (text != null) {
            outStream.write(text.getBytes("UTF-8"));
        }
        //TODO zip the file
        
        
        //byte[] buffer = new byte[4096];
        //int bytesRead = -1;
         
        //while ((bytesRead = sr.read(buffer)) != -1) {
        //    outStream.write(buffer, 0, bytesRead);
        //}
         
        //inStream.close();
        //sr.close();
        outStream.close(); 

    }
    
    protected String buildJSONString(ExportOptions exportOptions) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        
        AccountManager accountManager = context.getBean("accountManager",AccountManager.class);
        
        Map<String,Object> resultObject = new HashMap<String,Object>();
        List<Account> accounts = accountManager.getAccounts();

        if (exportOptions.isIncludeAccounts()){
            resultObject.put("accounts", accounts);
        }
        
        RegisterManager registerManager = context.getBean("registerManager",RegisterManager.class);
        ScheduledTransactionManager scheduledTransactionmanager = context.getBean("scheduledTransactionManager",ScheduledTransactionManager.class);
        Map<String,Object> transactionMap = new HashMap<String,Object>();
        Map<String,Object> scheduledTransactionMap = new HashMap<String,Object>();
        for (Account account:accounts) {
            if (exportOptions.isIncludeTransactions()){
                transactionMap.put(account.getId(), registerManager.getTransactions(account));
            }
            if (exportOptions.isIncludeSchedule()) {
                scheduledTransactionMap.put(account.getId(),scheduledTransactionmanager.getOriginalTransactions(account));
        
            }
        }
        if (exportOptions.isIncludeTransactions()){
            resultObject.put("transactions", transactionMap);
        }
        if (exportOptions.isIncludeSchedule()) {
            resultObject.put("schedule", scheduledTransactionMap);
        }
        
        if (exportOptions.isIncludeBudget()){
            BudgetManager budgetManager = context.getBean("budgetManager",BudgetManager.class);
            resultObject.put("budgets",budgetManager.getBudgets());
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result =  mapper.writeValueAsString(resultObject);
            return result;
        } catch (IOException ex) {
            log.error("Error in creating json",ex);
        }
        return null;
    }
    

}
