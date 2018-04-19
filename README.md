## oauth endpoints (resource-owner/password flow)
##### Token: baseUrl/oauth/token?usename={username}&password={password}&grant_type=password&client_secret={secret}&client_id (Authorization: Basic Base64(clientid:clientsecret))
##### Refresh Token: baseUrl/oauth/token?usename={username}&password={password}&grant_type=refresh_token&client_secret={secret}&client_id (Authorization: Bearer {token})
##### Logout (defined in OAuth2ResourceServerConfig) baseUrl/logout (Authorization: Bearer {token})

######If no password encoder bean is defined in the AuthServer config, then {noop} must be appended to the the client_secret (i.e., 'secret' becomes '{noop}secret').  This tells spring that the password is not encoded. 