package com.hf.hfw.files;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.hfw.accounts.events.AccountFileEvent;
import com.hf.hfw.application.ConfigurationDirectoryService;
import com.hf.hfw.manager.AccountManager;
import com.hf.homefinanceshared.Account;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author pldorrell
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/fileupload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class FileUploadServlet extends HttpServlet {

    /**
     * handles file upload
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        if ("importbackup".equalsIgnoreCase(operation)) {
            this.processSystemImport(request, response);
        } else if ("importonlinetxnfile".equalsIgnoreCase(operation)) {
            this.processAccountOnlineUpload(request, response);
        }
    }

    protected void processAccountOnlineUpload(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        //save the file
        ConfigurationDirectoryService configurationDirectoryService = (ConfigurationDirectoryService) context.getBean("configurationDirectoryService");
        String savePath = configurationDirectoryService.getFileStorageDirectory();

        //trigger event to process the file through the account manage
        //SpringifiedJobbie jobbie = (SpringifiedJobbie)context.getBean("springifiedJobbie");
        // gets absolute path of the web application
        //String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        //String savePath = appPath + File.separator + SAVE_DIR;
        // creates the save directory if it does not exists
        System.out.println("Saving to directory:"+savePath);
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        String accountId=null;
        String fullFilePath=null; 
        String finalFilePath = null;
        for (Part part : request.getParts()) {
            if (part.getName().equals("accountId")) {
                Scanner scanner = new Scanner(part.getInputStream());
                accountId = scanner.nextLine();
            
            } else if (part.getName().equals("file")) {
                String fileName = extractFileName(part);
                System.out.println("fileName From Post:"+fileName);
                fileName = UUID.randomUUID().toString() + "-" + fileName.replaceAll(" ", "");
                fullFilePath = configurationDirectoryService.getTempFileStorageDirectory() + File.separator + fileName;
                finalFilePath = configurationDirectoryService.getFileStorageDirectory()+File.separator+fileName;
                System.out.println("full fileName to write:"+fullFilePath);
                //write to the temp dir
                part.write(fullFilePath);
                
                
                //move from the temp dir to the file dir
                //File file=new File(fullFilePath);
                //Files.move(null, null, options)
                //Path moveFrom = FileSystems.getDefault().getPath(configurationDirectoryService.getTempFileStorageDirectory()+File.separator+fileName);
                Path moveFrom = FileSystems.getDefault().getPath(fullFilePath);
                Path moveTo = FileSystems.getDefault().getPath(finalFilePath);
                Files.move(moveFrom, moveTo, StandardCopyOption.ATOMIC_MOVE);
            }   

        }

        //TODO kickoff the event to say the file is written
        AccountManager accountManager = context.getBean("accountManager",AccountManager.class);
        Account account = accountManager.getAccountById(accountId);
        this.fireAccountEvent(account, finalFilePath, AccountEvent.AccountEventType.UPLOADED_TRANSACTION_FILE);
        request.setAttribute("message", "Upload has been done successfully!");
        getServletContext().getRequestDispatcher("/app/uploadresults").forward(request, response);        
    }
    
    protected void processSystemImport(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException{
        request.setAttribute("message", "Upload has been done successfully!");
        getServletContext().getRequestDispatcher("/app/uploadresults").forward(request, response);        
        
    }
    
    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            System.out.println(s);
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    protected void fireAccountEvent(Account _account, String fileName, AccountEvent.AccountEventType _type) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        AccountFileEvent event = new AccountFileEvent(context, _account, _type);
        event.setFileName(fileName);
        context.publishEvent(event);
    }
}
