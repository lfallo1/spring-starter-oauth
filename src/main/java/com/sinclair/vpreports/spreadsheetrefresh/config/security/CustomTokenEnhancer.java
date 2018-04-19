package com.sinclair.vpreports.spreadsheetrefresh.config.security;

import com.sinclair.vpreports.spreadsheetrefresh.config.security.model.CustomUserPasswordAuthenticationToken;
import com.sinclair.vpreports.spreadsheetrefresh.config.security.model.UserPrivileges;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    private static final Logger LOGGER = LogManager.getLogger(CustomTokenEnhancer.class);

    private List<TokenEnhancer> delegates = Collections.emptyList();

    public void setTokenEnhancers(List<TokenEnhancer> delegates) {
        this.delegates = delegates;
    }

    /**
     * Creating customized response
     *
     * @param accessToken
     * @param authentication
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        LOGGER.info("Inside enhance of CustomTokenEnhancer");

        final DefaultOAuth2AccessToken tempResult = (DefaultOAuth2AccessToken) accessToken;

        if (authentication != null) {

            final Map<String, Object> additionalInformation = new HashMap<String, Object>();

            final CustomUserPasswordAuthenticationToken customToken = (CustomUserPasswordAuthenticationToken) authentication
                    .getUserAuthentication();

            // Adding customized response
            if (customToken != null) {
                final UserPrivileges userPrivileges = customToken.getUserPrivileges();
                if (userPrivileges != null) {
                    additionalInformation.put("user_details", customToken.getUserPrivileges());
                    tempResult.setAdditionalInformation(additionalInformation);
                }
            } else {
                additionalInformation.put("error", "error attaching user_details to auth");
                tempResult.setAdditionalInformation(additionalInformation);
            }
        }
        OAuth2AccessToken result = tempResult;
        for (TokenEnhancer enhancer : delegates) {
            result = enhancer.enhance(result, authentication);
        }
        return result;
    }

}
