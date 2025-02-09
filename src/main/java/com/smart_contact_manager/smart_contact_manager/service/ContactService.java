package com.smart_contact_manager.smart_contact_manager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smart_contact_manager.smart_contact_manager.model.Contact;
import com.smart_contact_manager.smart_contact_manager.model.User;

public interface ContactService {
    public Contact save(Contact contact);
    public Contact update(Contact contact);
    public List<Contact> getAll();
    public Contact getContactById(String id);
    public void delete(String id);
//    List<Contact>search(String name,String email,String phoneNumber);
    Page<Contact>searchByName(String name,int size,int page,String sortBy,String order,User user);
    Page<Contact>searchByEmail(String email,int size,int page,String sortBy,String order,User user);
    Page<Contact>searchByPhone(String phone,int size,int page,String sortBy,String order,User user);
    List<Contact>getContactByUserId(String userId);
    Page<Contact> getByUser(User user,int page,int size,String sortField,String sortDirection);
}
