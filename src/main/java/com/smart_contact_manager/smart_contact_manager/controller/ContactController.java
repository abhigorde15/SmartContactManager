package com.smart_contact_manager.smart_contact_manager.controller;

import java.util.List;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.smart_contact_manager.smart_contact_manager.forms.ContactForm;
import com.smart_contact_manager.smart_contact_manager.forms.ContactSearchForm;
import com.smart_contact_manager.smart_contact_manager.helpers.AppConstants;
import com.smart_contact_manager.smart_contact_manager.helpers.Helper;
import com.smart_contact_manager.smart_contact_manager.model.Contact;
import com.smart_contact_manager.smart_contact_manager.model.User;
import com.smart_contact_manager.smart_contact_manager.service.ContactService;
import com.smart_contact_manager.smart_contact_manager.service.ImageService;
import com.smart_contact_manager.smart_contact_manager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	@Autowired
	private ContactService contactService;
	@Autowired
	private ImageService imageService;
	private Logger logger = LoggerFactory.getLogger(ContactController.class);
	@Autowired
	private UserService userService;
	@RequestMapping("/add")
   public String addContactView(Model model) {
		ContactForm contactForm = new ContactForm();
		model.addAttribute("contactForm", contactForm);
		//contactForm.setEmail("abhishek");
	   return "user/add_contact";
   }
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String saveContact(@ModelAttribute("contactForm") ContactForm contactForm
,Authentication authentication,HttpSession session) {
		
		Contact contact = new Contact();
		String username = Helper.getEmailOfLoggerInUser(authentication);
		User user = userService.getUserByEmail(username);
		logger.info("file information: {}",contactForm.getPicture().getOriginalFilename());
		
		contact.setName(contactForm.getName());
		contact.setAdress(contactForm.getAdress());
		contact.setDescription(contactForm.getDescription());
		contact.setEmail(contactForm.getEmail());
		contact.setLinkedInList(contactForm.getLinkedInLink());
		contact.setInstagram_link(contactForm.getInstagram_link());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setUser(user);
		contact.setFavorite(contactForm.isFavorite());
		
		if(contactForm.getPicture()!= null && !contactForm.getPicture().isEmpty()) {
    		String filename = UUID.randomUUID().toString();
        	String fileUrl = imageService.uploadImage(contactForm.getPicture(),filename);
        	contact.setPicture(fileUrl);
        	logger.info("if"+fileUrl);
        	contact.setCloudinaryImagePublicId(filename);
    	}else {
    	    contact.setPicture("https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=1024x1024&w=is&k=20&c=-mUWsTSENkugJ3qs5covpaj-bhYpxXY-v9RDpzsw504="); // Set a default image URL if required
    	    contact.setCloudinaryImagePublicId(null);
    	    logger.info("elese");
    	    // Allow null if not required
    	}
		logger.info("Saving........");
		contactService.save(contact);
		
		return "redirect:/user/contacts/add";
	}

    @RequestMapping
	public  String viewContacts(Authentication authentication,Model model,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value = "size",defaultValue="3") int size,
			@RequestParam(value="sortBy",defaultValue="name") String sortBy,
			@RequestParam(value="direction",defaultValue="asc") String direction
			
			) {
    	String userName =  Helper.getEmailOfLoggerInUser(authentication);
    	//System.out.println("UserName "+userName);
    	User user  = userService.getUserByEmail(userName);
    	
    	Page<Contact>pageContact =  contactService.getByUser(user,page,size,sortBy,direction);
    //	System.out.println("pageContact "+pageContact);
    	model.addAttribute("contactSearchForm",new ContactSearchForm());
    	model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
    //	System.out.println("contacts......................"+contacts);
    	model.addAttribute("pageContact",pageContact);
    	
    	return "user/contacts";
    }
    @RequestMapping("/search")
    public String searchHandler(

    	@ModelAttribute ContactSearchForm contactSearchForm,
    	@RequestParam(value="page", defaultValue="0") int page,
		@RequestParam(value = "size",defaultValue="3") int size,
		@RequestParam(value="sortBy",defaultValue="name") String sortBy,
		@RequestParam(value="direction",defaultValue="asc") String direction,
		Model model,
		Authentication authentication
    		) {
    	var user = userService.getUserByEmail(Helper.getEmailOfLoggerInUser(authentication));
    	Page<Contact> pageContact =null;
    	if(contactSearchForm.getField().equalsIgnoreCase("name")) {
    	   pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,user);
    	}
    	else if(contactSearchForm.getField().equalsIgnoreCase("email")) {
   		   pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,user);
   	}
    	else if(contactSearchForm.getField().equalsIgnoreCase("phone")) {
    		 pageContact = contactService.searchByPhone(contactSearchForm.getValue(), size, page, sortBy, direction,user);
    	}
    	else {
    	      return "redirect:/user/contacts";
    	}
    	
    	model.addAttribute("pageSize", size);

    	model.addAttribute("contactSearchForm",new ContactSearchForm());
//    	logger.info("PageContact"+pageContact);
//    	logger.info("Total Elements: " + pageContact.getTotalElements());
//    	logger.info("Total Pages: " + pageContact.getTotalPages());
        model.addAttribute("pageContact",pageContact);
    //	logger.info("filed: {} Keyword : {}",field,value);
    	return "user/search";
    }
    
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId,
    		HttpSession session) {
    	contactService.delete(contactId);
//    	session.setAttribute("message",Message.builder().content( "Contact is deleted Successfully")
//    			.type(MessageType.green).build()
//    			);
    	
    	return "redirect:/user/contacts";
    }
    @RequestMapping("/view/{contactId}")
    public String viewContact(@PathVariable String contactId,
    		Model model
    		) {
    	
    	var contact = contactService.getContactById(contactId);
    	ContactForm contactForm = new ContactForm();
    	contactForm.setName(contact.getName());
    	contactForm.setEmail(contact.getEmail());
    	contactForm.setAdress(contact.getAdress());
    	contactForm.setDescription(contact.getDescription());
    	contactForm.setPhoneNumber(contact.getPhoneNumber());
    	contactForm.setLinkedInLink(contact.getLinkedInList());
    	contactForm.setInstagram_link(contact.getInstagram_link());
    	model.addAttribute(contactForm);
    	
    	return "user/update_contact_view"; 
    }
    @RequestMapping(value="/update/{contactId}", method=RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,@ModelAttribute ContactForm contactForm,Model model) {
    	var contact = contactService.getContactById(contactId);
    	if(contactForm.getPicture()!= null && !contactForm.getPicture().isEmpty()) {
    		String filename = UUID.randomUUID().toString();
        	String fileUrl = imageService.uploadImage(contactForm.getPicture(),filename);
        	contact.setPicture(fileUrl);
    	}
    	
    	contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setAdress(contactForm.getAdress());
    	contact.setDescription(contactForm.getDescription());
    	contact.setPhoneNumber(contactForm.getPhoneNumber());
    	contact.setLinkedInList(contactForm.getLinkedInLink());
    	contact.setInstagram_link(contactForm.getInstagram_link());
    	
    	contact.setId(contactId);
    	var updateContact = contactService.update(contact);
    	logger.info("UpdateContact"+updateContact);
    	logger.info("Name"+updateContact.getName());
    	logger.info("Picture"+updateContact.getPicture());

    	return "redirect:/user/contacts/view/"+contactId;
    }
    

}
