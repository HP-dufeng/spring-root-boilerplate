package example.service;

import example.oauth.TokenVerifyService;
import example.oauth.dto.UserProfile;
import example.service.dto.JwtUserProfile;
import example.service.dto.WUCCUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class JwtService implements TokenVerifyService {

    @Value("${token_verify_url}")
    private String token_verify_url ;

    public UserProfile verify(String token) throws RestClientException {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<WUCCUser> response = restTemplate.exchange(
                token_verify_url,
                HttpMethod.GET,
                entity,
                WUCCUser.class);


        return new JwtUserProfile(response.getBody());

//            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);


//        JwtUserProfile user = new JwtUserProfile();
//
//        String initList[] = { "ROLE_ADMIN", "Two", "Four", "One",};
//        List list = new ArrayList(Arrays.asList(initList));
//        user.setRole(list);




    }




}

