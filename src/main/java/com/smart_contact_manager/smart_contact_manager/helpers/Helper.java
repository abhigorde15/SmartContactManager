package com.smart_contact_manager.smart_contact_manager.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    public static String getEmailOfLoggerInUser(Authentication authentication) {
    	 
    	if(authentication instanceof OAuth2AuthenticationToken) {
    		var aOAuthenticationToken = (OAuth2AuthenticationToken)authentication; 
    	    var clientId = aOAuthenticationToken.getAuthorizedClientRegistrationId();
    	    var oauth2User = (OAuth2User) authentication.getPrincipal();
    	   String username ="";
    	    if(clientId.equalsIgnoreCase("google")) {
    	    	username = oauth2User.getAttribute("email").toString();
    	    }
    	    else if(clientId.equalsIgnoreCase("github")) {
    	    	username = (oauth2User.getAttribute("email") != null)
    		            ? oauth2User.getAttribute("email").toString()
    		            : oauth2User.getAttribute("login").toString() + "@gmail.com";
    	    }
    	    return username;
    	}
    	else {
    		
    		return authentication.getName();
    	}
    	
    	
    }
    public static String getLinkForEmailVerification(String emailToken) {
    	String link = "http://localhost:8080/auth/verify-email?token="+emailToken;
        return link;
    }
}
