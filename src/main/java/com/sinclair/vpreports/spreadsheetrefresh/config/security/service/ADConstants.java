package com.sinclair.vpreports.spreadsheetrefresh.config.security.service;

public interface ADConstants {

    String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    String PROVIDER_URL = "ldap://10.99.50.5:389";

    String SIMPLE_SECURITY_AUTHENTICATION = "simple";

    String SERVER_SECURITY_PRINCIPAL = "CN=Corporate Service Account svc_palasauth,OU=ServiceAccounts,DC=sbgnet,DC=int";

    String SERVER_SECURITY_CREDENTIALS = "Pa1a5aut41!";

    String SERVER_BASEDN = "DC=sbgnet,DC=int";

    String MEMBER_OF = "memberOf";

    String CN = "cn";
}
