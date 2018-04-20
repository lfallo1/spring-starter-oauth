package com.sinclair.vpreports.spreadsheetrefresh.config.security.service;

import com.sinclair.vpreports.spreadsheetrefresh.config.security.model.UserPrivileges;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;

@Service
public class LdapService implements ADConstants {

    private static final Logger LOGGER = LogManager.getLogger(LdapService.class);

    public Boolean authenticate(String adUserName, String adPassword) {

        Boolean authenticated = false;
        LOGGER.info("Searching for credentials in AD server");
        DirContext ctx = null;
        DirContext ctxUser = null;
        Hashtable env = new Hashtable();
        String binddn = null;
        UserPrivileges userPrivileges = null;
        // Create the initial context by connecting to server
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, PROVIDER_URL);
        env.put(Context.SECURITY_AUTHENTICATION, SIMPLE_SECURITY_AUTHENTICATION);
        env.put(Context.SECURITY_PRINCIPAL, SERVER_SECURITY_PRINCIPAL);
        env.put(Context.SECURITY_CREDENTIALS, SERVER_SECURITY_CREDENTIALS);

        try {
            ctx = new InitialDirContext((Hashtable) env);
            final String[] returnAttribute = {CN};
            final SearchControls srchControls = new SearchControls();
            srchControls.setReturningAttributes(returnAttribute);
            srchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            final String searchFilter = "(&(sAMAccountName=" + adUserName + "))";
            final NamingEnumeration srchResponse = ctx.search(SERVER_BASEDN, searchFilter, srchControls);

            while (srchResponse.hasMoreElements()) {

                final SearchResult res = (SearchResult) srchResponse.nextElement();
                binddn = res.getName() + "," + SERVER_BASEDN;
                LOGGER.info("User " + binddn);

                if (null != binddn) {
                    Hashtable envs = new Hashtable();
                    envs.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
                    envs.put(Context.PROVIDER_URL, PROVIDER_URL);
                    envs.put(Context.SECURITY_AUTHENTICATION, SIMPLE_SECURITY_AUTHENTICATION);
                    envs.put(Context.SECURITY_PRINCIPAL, binddn);
                    envs.put(Context.SECURITY_CREDENTIALS, adPassword);
                    ctxUser = new InitialDirContext((Hashtable) envs);

                    // Search for memberOf attribute to get the DB access list
                    final String[] returnAttributeForMemberOf = {CN, MEMBER_OF,};

                    final SearchControls srchCntrl = new SearchControls();
                    srchCntrl.setReturningAttributes(returnAttributeForMemberOf);
                    srchCntrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
                    final String searchFilters = "(&(sAMAccountName=" + adUserName + "))";

                    final NamingEnumeration srchResponseForMemberOf = (ctxUser).search(SERVER_BASEDN, searchFilters,
                            srchCntrl);

                    authenticated = srchResponseForMemberOf.hasMoreElements();
                }
            }
            if (ctxUser != null) {
                ctxUser.close();
            }
        } catch (NamingException e) {
            LOGGER.info(e.toString());
        }
        return authenticated;
    }

}
