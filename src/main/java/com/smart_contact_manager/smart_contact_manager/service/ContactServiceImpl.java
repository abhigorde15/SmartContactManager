package com.smart_contact_manager.smart_contact_manager.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smart_contact_manager.smart_contact_manager.exception.ResourceNotFoundException;
import com.smart_contact_manager.smart_contact_manager.model.Contact;
import com.smart_contact_manager.smart_contact_manager.model.User;
import com.smart_contact_manager.smart_contact_manager.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository repository;
    
	@Override
	public Contact save(Contact contact) {
		String contactId = UUID.randomUUID().toString();
		contact.setId(contactId);
		return repository.save(contact);
	}

	@Override
	public Contact update(Contact contact) {
		var contactOld = repository.findById(contact.getId()).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		contactOld.setName(contact.getName());
		contactOld.setEmail(contact.getEmail());
		contactOld.setAdress(contact.getAdress());
    	contactOld.setDescription(contact.getDescription());
    	contactOld.setPhoneNumber(contact.getPhoneNumber());
    	contactOld.setLinkedInList(contact.getLinkedInList());
    	contactOld.setInstagram_link(contact.getInstagram_link());
		contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
    	contactOld.setPicture(contact.getPicture());
    	if(contact.isFavorite()==true) {
    		contactOld.setFavorite(true);
    	}
		return repository.save(contactOld);
	}

	@Override
	public List<Contact> getAll() {
		
		return repository.findAll();
	}

	@Override
	public Contact getContactById(String id) {
		
		return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not Found with given id "+id));
	}

	@Override
	public void delete(String id) {
	  Contact contact =	repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not Found with given id "+id));
	  repository.delete(contact);
	}

	
	

	@Override
	public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
	    Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
	    Pageable pageable = PageRequest.of(page, size, sort);
	    
	  
//	    System.out.println("Fetching contacts for user ID: " + user.getUserId());
//	    System.out.println("Requested Page: " + page + ", Size: " + size);
	    
	    Page<Contact> contacts = repository.findByUser(user, pageable);
	    
	//   System.out.println("Total Contacts Found: " + contacts.getTotalElements());
	//   System.out.println("Total Pages Available: " + contacts.getTotalPages());

	    return contacts;
	}


	@Override
	public List<Contact> getContactByUserId(String userId) {
		
		return repository.findByUserId(userId);
	}

	@Override
	public Page<Contact> searchByName(String name, int size, int page, String sortBy, String order,User user) {
		Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page,size,sort);
		
		return repository.findByUserAndNameContaining(user,name, pageable);
	}

	@Override
	public Page<Contact> searchByEmail(String email, int size,int page, String sortBy, String order,User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page,size,sort);
		return repository.findByUserAndEmailContaining(user,email, pageable);
	}

	@Override
	public Page<Contact> searchByPhone(String phone,int size, int page, String sortBy, String order,User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page,size,sort);
		return repository.findByUserAndPhoneNumberContaining(user,phone, pageable);
	}

	

}
