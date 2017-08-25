package example.oauth;

import example.oauth.dto.UserProfile;

public interface TokenVerifyService {

    UserProfile verify(String token);
}
