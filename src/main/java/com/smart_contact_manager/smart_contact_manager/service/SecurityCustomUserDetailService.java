package com.smart_contact_manager.smart_contact_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart_contact_manager.smart_contact_manager.repository.UserRepository;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  return userRepository.findByEmail(username).orElseThrow(
           ()->new UsernameNotFoundException("User not found with email") );
	}
    
}
