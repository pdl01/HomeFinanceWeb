/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.h2;

import com.hf.hfw.accounts.tasks.AccountBalanceNotifierTask;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.h2.tools.Server;

/**
 *
 * @author pldorrell
 */
public class H2Server {
    private boolean started;
    //private static Server server;
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(H2Server.class);
/*
    public Server getServer() {
        if (H2Server.server != null) {
            try { 
                H2Server.server = Server.createTcpServer("-tcpPort","9123").start();
            } catch (SQLException ex) {
                log.error("Could not start the H2Server",ex);
            }
        }
        return server;

    }
    public void stop() {
        if (H2Server.server != null) {
          
            H2Server.server.stop(); 
          
        }

    }
    */
}
