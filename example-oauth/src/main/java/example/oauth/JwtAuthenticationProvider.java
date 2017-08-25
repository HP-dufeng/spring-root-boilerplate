package example.oauth;

import javax.inject.Inject;

import example.oauth.dto.UserProfile;
import example.oauth.exception.JwtAuthenticationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private TokenVerifyService jwtService;


    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String accessToken = (String)authentication.getPrincipal();
            UserProfile possibleProfile = jwtService.verify(accessToken);
            return new JwtAuthenticatedProfile(possibleProfile);
        } catch (Exception e) {
            throw new JwtAuthenticationException("Failed to verify token", e);
        }

    }

    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.equals(authentication);
    }
}
