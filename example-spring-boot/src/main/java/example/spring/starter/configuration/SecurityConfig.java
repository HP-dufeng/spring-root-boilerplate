package example.spring.starter.configuration;

import javax.inject.Inject;

import example.oauth.JwtAuthFilter;
import example.oauth.JwtAuthenticationEntryPoint;
import example.oauth.JwtAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private JwtAuthFilter jwtAuthFilter;

    @Inject
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Inject
    private JwtAuthenticationEntryPoint jwtAuthEndPoint;

    @Override
    public void configure(AuthenticationManagerBuilder auth)  throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }



    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] patterns = new String[] {
                "/swagger-ui/**",
                "/api-docs/**"
        };

        http.authorizeRequests()
                .antMatchers(patterns)
                .permitAll()
                .antMatchers("/**/*")
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEndPoint);
    }

}

