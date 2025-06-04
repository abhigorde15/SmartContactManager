package com.smart_contact_manager.smart_contact_manager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.smart_contact_manager.smart_contact_manager.model.Providers;
import com.smart_contact_manager.smart_contact_manager.model.User;
import com.smart_contact_manager.smart_contact_manager.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
	
    @Autowired
    private UserRepository userRepository;
    
    @Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		  logger.info("OAuthenticationSuccess");
	  var oauth2AuthenticationToken =(OAuth2AuthenticationToken)authentication;
	  String authorizedClientRegId =  oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
	  
	  var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();
	//  System.out.println("Oauthuser.............................."+oauthUser);
	  User user = new User();
	  user.setUserId(UUID.randomUUID().toString());
	  user.setRoleList(List.of("ROLE_USER"));
	  user.setEmailVerified(true);
	  user.setEnabled(true);
	  user.setPassword("dummy");
	  if(authorizedClientRegId.equalsIgnoreCase("google")) {
		  user.setEmail(oauthUser.getAttribute("email").toString());
		  user.setName(oauthUser.getAttribute("name").toString());
		  user.setProfilePict(oauthUser.getAttribute("picture").toString());
		  user.setProviderUserId(oauthUser.getName());
		  user.setProvider(Providers.GOOGLE);
	  }
	  else if((authorizedClientRegId.equalsIgnoreCase("github"))) {
		  String email = (oauthUser.getAttribute("email") != null)
		            ? oauthUser.getAttribute("email").toString()
		            : oauthUser.getAttribute("login").toString() + "@gmail.com";
		 String picture = oauthUser.getAttribute("avatar_url").toString();
		 String name = oauthUser.getAttribute("login").toString();
		 String providerUserId = oauthUser.getName();
		 
		 user.setEmail(email);
		 user.setProfilePict(picture);
		 user.setName(name);
		 user.setProvider(Providers.GITHUB);
		 user.setProviderUserId(providerUserId);
		 
	  }
//		  logger.info(user.getName());
//		 user.getAttributes().forEach((key,value)->{
//			  logger.info("{} => {}",key,value);
//			  logger.info(user.getAuthorities().toString());
//		  });
//		
//		 String email = user.getAttribute("email").toString();
//		 String name = user.getAttribute("name").toString();
//		 String picture = user.getAttribute("picture").toString();
//		 
//		
//		 User user1 = new User();
//		 user1.setEmail(email);
//		 user1.setName(name);
//		 user1.setProfilePict(picture);
//		 user1.setPassword("password");
//		 user1.setUserId(UUID.randomUUID().toString());
//		 user1.setProvider(Providers.GOOGLE);
//		 user1.setEnabled(true);
//		 user1.setEmailVerified(true);
//		 user1.setProviderUserId(user.getName());
//		 user1.setRoleList(List.of("ROLE_USER"));
//		 user1.setAbout("This account is created using google");
		
	  User user2 = userRepository.findByEmail(user.getEmail()).orElse(null);
		 if(user2 == null) {
			 userRepository.save(user);
			 logger.info("User saved "+user);
		 }
		 
		 new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
	}

}
