/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.files;

import com.hf.hfw.accounts.events.AccountEvent;
import com.hf.hfw.accounts.events.AccountFileEvent;
import com.hf.hfw.application.ConfigurationDirectoryService;
import com.hf.hfw.manager.AccountManager;
import com.hf.homefinanceshared.Account;
import java.io.File;
import java.io.IOException;
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
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        String accountId=null;
        String fullFilePath=null; 
        for (Part part : request.getParts()) {
            if (part.getName().equals("accountId")) {
                Scanner scanner = new Scanner(part.getInputStream());
                accountId = scanner.nextLine();
            } else {
                String fileName = extractFileName(part);

                fileName = UUID.randomUUID().toString() + "-" + fileName.replaceAll(" ", "");
                fullFilePath = savePath + File.separator + fileName;
                part.write(fullFilePath);
            }

        }

        //TODO kickoff the event to say the file is written
        AccountManager accountManager = context.getBean("accountManager",AccountManager.class);
        Account account = accountManager.getAccountById(accountId);
        this.fireAccountEvent(account, fullFilePath, AccountEvent.AccountEventType.UPLOADED_TRANSACTION_FILE);
        request.setAttribute("message", "Upload has been done successfully!");
        getServletContext().getRequestDispatcher("/uploadresults.jsp").forward(
                request, response);
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
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
