package example.oauth.dto;

import java.util.Collection;

public interface UserProfile {

    Collection<String> getRole();
    Object getPrincipal();
}
