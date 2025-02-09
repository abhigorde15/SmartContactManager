package com.smart_contact_manager.smart_contact_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_contact_manager.smart_contact_manager.model.Contact;
import com.smart_contact_manager.smart_contact_manager.service.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ContactService contactService;
    @GetMapping("/contacts/{contactId}")
	public Contact getContact(@PathVariable String contactId) {
		return contactService.getContactById(contactId);
	}
}
