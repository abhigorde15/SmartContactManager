package com.smart_contact_manager.smart_contact_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.smart_contact_manager.smart_contact_manager.helpers.Helper;
import com.smart_contact_manager.smart_contact_manager.model.User;
import com.smart_contact_manager.smart_contact_manager.service.UserService;

@ControllerAdvice
public class RootController {
   @Autowired
   private UserService userService;
   
   @ModelAttribute
	public void addLoggedInUserInfo(Model model,Authentication authentication) {
	   if(authentication == null) return;
	   String username = Helper.getEmailOfLoggerInUser(authentication);
		
		User user = userService.getUserByEmail(username);
		model.addAttribute("loggedUser",user);
	}
		
	
}
