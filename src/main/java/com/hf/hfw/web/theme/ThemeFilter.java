package com.hf.hfw.web.theme;

import com.hf.hfw.api.v1.settings.SettingsBean;
import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.manager.SettingsManager;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author pldorrell
 */
public class ThemeFilter extends OncePerRequestFilter {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ThemeFilter.class);
    private SettingsManager settingsManager;

    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("request is " + request.getClass());
        log.info("request URL : " + request.getRequestURL());
        log.info("response is " + response.getClass());
        
        SettingsBean themeBean = this.settingsManager.getThemeSetting();
        
        request.setAttribute("theme",themeBean.getSettings().get("theme"));
        filterChain.doFilter(request, response);
    }

}
