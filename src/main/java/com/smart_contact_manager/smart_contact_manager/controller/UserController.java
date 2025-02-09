package com.smart_contact_manager.smart_contact_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart_contact_manager.smart_contact_manager.helpers.Helper;
import com.smart_contact_manager.smart_contact_manager.model.User;
import com.smart_contact_manager.smart_contact_manager.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
  //1.user dashboard
	@GetMapping("/dashboard")
	public String userDashboard() {
		return "user/dashboard";
	}
	
	@PostMapping("/dashboard")
	public String userDashboardPost() {
		return "user/dashboard";
	}
	
	@GetMapping("/profile")
	public String userProfile(Authentication authentication,Model model) {
		
	//	System.out.println(user.getName()+","+user.getEmail());
		return "user/profile";
	}
	
  //2.user add contact page
	
 //3.user view contact
	
 //4.	
}
