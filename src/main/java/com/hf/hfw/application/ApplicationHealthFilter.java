package com.hf.hfw.application;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author pldorrell
 */
public class ApplicationHealthFilter extends OncePerRequestFilter{
    private static final Logger log = LogManager.getLogger(ApplicationHealthFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!ApplicationState.getApplicationState().isConfigured()) {
            response.sendError(503,"Repo is not configured");
        } else if (!ApplicationState.getApplicationState().isRepositoryRunning()){
            //response.sendError(503,"Repo is not running");
         //redirect to down page
        }
        filterChain.doFilter(request, response);
    }
    
}
