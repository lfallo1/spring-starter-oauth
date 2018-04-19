package com.sinclair.vpreports.spreadsheetrefresh.config.security.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutHandler implements LogoutSuccessHandler {
	
	@Autowired
	private TokenStore inMemoryTokenStore;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String token = request.getHeader("Authorization");

		if (token != null && token.startsWith("Bearer")) {

			OAuth2AccessToken oAuth2AccessToken = inMemoryTokenStore.readAccessToken(token.split(" ")[1]);

			if (oAuth2AccessToken != null) {
				inMemoryTokenStore.removeAccessToken(oAuth2AccessToken);
			}

		}

		response.setStatus(HttpServletResponse.SC_OK);
	}
}
