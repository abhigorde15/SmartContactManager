package com.smart_contact_manager.smart_contact_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart_contact_manager.smart_contact_manager.model.User;
import com.smart_contact_manager.smart_contact_manager.repository.UserRepository;
import com.smart_contact_manager.smart_contact_manager.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
    private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/verify-email")
	public String verifyEmail(
		@RequestParam("token")String token	
			) {
		User user = userService.getUserByToken(token);
		
		if(user != null) {
			
			if(user.getEmailToken().equals(token)) {
				user.setEmailVerified(true);
				user.setEnabled(true);
				userRepository.save(user);
				return "success_page";
			}
			return "error_page";
		}
	
		return "error_page";
	}
}
