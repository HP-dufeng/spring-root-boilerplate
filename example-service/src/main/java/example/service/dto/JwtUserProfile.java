package example.service.dto;

import java.util.Collection;

import example.oauth.dto.UserProfile;
import org.springframework.stereotype.Component;


@Component
public class JwtUserProfile implements UserProfile {

    private Collection<String> role;
    private Object principal;

    public JwtUserProfile(WUCCUser user) {
        this.principal = user;
        this.role = user == null?null:user.getRole();
    }

    public Collection<String> getRole() {
        return role;
    }

    public Object getPrincipal() {
        return principal;
    }

}
