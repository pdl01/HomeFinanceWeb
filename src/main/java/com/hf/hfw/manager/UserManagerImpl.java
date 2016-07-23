package com.hf.hfw.manager;

import com.hf.hfw.api.v1.settings.SettingsBean;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author pldorrell
 */
public class UserManagerImpl implements UserDetailsService {

    SettingsManager settingsManager;

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SettingsBean settingsBean = this.settingsManager.getLimitedSecurityUsers();
        settingsBean.getSettings();
        UserDetails userDetails;

        String user1UserName = settingsBean.getSettings().get("user1.name");
        String user2UserName = settingsBean.getSettings().get("user2.name");
        String user3UserName = settingsBean.getSettings().get("user3.name");
        String password = null;
        boolean userFound = false;
        if (userName.equals(user1UserName)) {
            userFound = true;
            password = settingsBean.getSettings().get("user1.password");
        } else if (userName.equals(user2UserName)) {
            userFound = true;
            password = settingsBean.getSettings().get("user2.password");
        } else if (userName.equals(user3UserName)) {
            userFound = true;
            password = settingsBean.getSettings().get("user3.password");
        }

        if (userFound) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new ExplicitUserRoleAuthority());
            userDetails = new User(userName, password, true, true, true, true, authorities);

            return userDetails;

        } else {
            throw new UsernameNotFoundException(userName + " is invalid");
        }
    }

    class ExplicitUserRoleAuthority implements GrantedAuthority {

        @Override
        public String getAuthority() {
            return "ROLE_USER";
        }

    }
}
