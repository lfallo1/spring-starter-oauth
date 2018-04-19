package com.sinclair.vpreports.spreadsheetrefresh.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private LogoutSuccessHandler logoutHandler;

    /**
     * Use this to configure the access rules for secure resources. By default all resources <i>not</i> in "/oauth/**"
     * are protected (but no specific rules about scopes are given, for instance). You also get an
     * {@link OAuth2WebSecurityExpressionHandler} by default.
     *
     * @param http the current http filter configuration
     * @throws Exception if there is a problem
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated().and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler()).and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(logoutHandler);
    }

}
