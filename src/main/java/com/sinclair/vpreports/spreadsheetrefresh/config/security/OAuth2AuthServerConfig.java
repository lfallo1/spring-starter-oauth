package com.sinclair.vpreports.spreadsheetrefresh.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientService;

    @Autowired
    private UserApprovalHandler handler;

    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @Autowired
    private TokenEnhancer tokenEnhancer;

    //auth server config

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        //-- CLIENT CREDENTIALS --
//		clients.inMemory()
//				.withClient("trustedclient")
//				.authorizedGrantTypes("client_credentials")
//				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//				.scopes("read", "write", "trust")
//				.secret("secret")
//				.accessTokenValiditySeconds(120)
//				.refreshTokenValiditySeconds(600);

        //-- RESOURCE OWNER PASSWORD FLOW --
        clients.inMemory()
                .withClient("trustedclient")
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .secret("{noop}secret")
                .accessTokenValiditySeconds(6000)
                .refreshTokenValiditySeconds(12000);

    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancer)
                .userApprovalHandler(handler)
                .authenticationManager(authenticationManagerBean);
    }

    //token store

    @Bean
    @Qualifier("inMemoryTokenStore")
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientService));
        handler.setClientDetailsService(clientService);
        return handler;
    }

    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }
}
