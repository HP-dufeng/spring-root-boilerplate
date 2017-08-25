package example.oauth;

import java.util.ArrayList;
import java.util.Collection;

import example.oauth.dto.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtAuthenticatedProfile implements Authentication {

    @Autowired
    private UserProfile userProfile;

    public JwtAuthenticatedProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();

        Collection<String> roles = userProfile.getRole();
        if(roles!=null && (!roles.isEmpty())) {
            for (String s : roles) {
                list.add(new SimpleGrantedAuthority(s));
            }
        }

        return list;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userProfile.getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}

