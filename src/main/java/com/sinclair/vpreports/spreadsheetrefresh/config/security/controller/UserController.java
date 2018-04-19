package com.sinclair.vpreports.spreadsheetrefresh.config.security.controller;

import com.sinclair.vpreports.spreadsheetrefresh.config.security.model.CustomUserPasswordAuthenticationToken;
import com.sinclair.vpreports.spreadsheetrefresh.config.security.model.UserPrivileges;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * endpoint to retrieve the currently authenticated user
 *
 * @author lancefallon
 */
@RestController
@RequestMapping("/oauth/user")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    /**
     * get authenticated user.
     * authentication determined by a valid access_token
     *
     * @param auth
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserPrivileges> getUser(OAuth2Authentication auth) {
        CustomUserPasswordAuthenticationToken token = (CustomUserPasswordAuthenticationToken) auth.getUserAuthentication();
        return new ResponseEntity<>(token.getUserPrivileges(), HttpStatus.OK);
    }

}
