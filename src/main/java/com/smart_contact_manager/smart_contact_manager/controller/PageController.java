package com.smart_contact_manager.smart_contact_manager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart_contact_manager.smart_contact_manager.forms.UserForm;
import com.smart_contact_manager.smart_contact_manager.model.User;
import com.smart_contact_manager.smart_contact_manager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {
	
	@Autowired
	private UserService userService;
	@GetMapping("/home")
    public String homePage(org.springframework.ui.Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    boolean isLoggedIn = auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");
	    System.out.println("isLoggedIn"+isLoggedIn);
    	model.addAttribute("isLoggedIn",isLoggedIn);
	    return "home";
    }
	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}
	@GetMapping("/about")
	public String aboutPage() {
		return "about";
	}
	@GetMapping("/services")
	public String servicesPage() {
		return "services";
	}
	@GetMapping("/contact")
	public String contactPage() {
		return "contact";
	}
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	@GetMapping("/signup")
	public String signUpPage(Model model) {
		UserForm userForm = new UserForm();
		
		model.addAttribute("userForm",userForm);
		return "register";
	}
	
	   
	

	@PostMapping("/do-register")
	public String processRegister(@Valid @ModelAttribute UserForm userForm
			,BindingResult bindingResult,HttpSession session) {
	   
		if(bindingResult.hasErrors()) {
			return "register";
		}
	   User user =new User();
	   user.setName(userForm.getName());
	   user.setAbout(userForm.getAbout());
	   user.setPassword(userForm.getPassword());
	   user.setPhone(userForm.getPhone());
	   user.setEmail(userForm.getEmail());
	   user.setEnabled(false);
	   user.setProfilePict("https://commons.wikimedia.org/wiki/File:Default_pfp.jpg");
	   
		User savedUser = userService.saveUser(user);
		System.out.println("Saved a user");
		return "redirect:/signup";
	}
	
}
