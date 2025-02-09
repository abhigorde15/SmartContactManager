package com.smart_contact_manager.smart_contact_manager.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart_contact_manager.smart_contact_manager.exception.ResourceNotFoundException;
import com.smart_contact_manager.smart_contact_manager.helpers.Helper;
import com.smart_contact_manager.smart_contact_manager.model.User;
import com.smart_contact_manager.smart_contact_manager.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public User saveUser(User user) {
		String userId  = UUID.randomUUID().toString();
		user.setUserId(userId);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user.setRoleList(List.of("ROLL_USER"));
		logger.info(user.getProvider().toString());
		
		String emailToken = UUID.randomUUID().toString();
		String emailLink = Helper.getLinkForEmailVerification(emailToken);
		user.setEmailToken(emailToken);
		User savedUser = userRepository.save(user);
		
		emailService.sendEmail(savedUser.getEmail(),"Verify Account Email of Smart Contact Manager", emailLink);
		return user;
	}

	@Override
	public Optional<User> getUserById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> updateUser(User user) {
		User user2 = userRepository.findById(user.getUserId())
				.orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		user2.setName(user.getName());
		user2.setAbout(user.getAbout());
		user2.setContacts(user.getContacts());
		user2.setEmail(user.getEmail());
		user2.setPassword(user.getPassword());
		user2.setPhone(user.getPhone());
		user2.setProfilePict(user.getProfilePict());
		user2.setEnabled(user.isEnabled());
		user2.setEmailVerified(user.isEmailVerified());
		user2.setPhoneVerified(user.isPhoneVerified());
		user2.setProvider(user.getProvider());
		user2.setProviderUserId(user.getProviderUserId());
		
		User save = userRepository.save(user2);
		
		return Optional.ofNullable(save);
	}

	@Override
	public boolean isUserExist(String id) {
		User user2 = userRepository.findById(id)
				.orElse(null);
		return user2 != null ? true : false;
	
	}

	@Override
	public boolean isUserExistByEmail(String email) {
		User user2 = userRepository.findByEmail(email)
				.orElse(null);
		return user2 != null ? true : false;
	
	}

	@Override
	public void deleteUser(String id) {
		User user2 = userRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		userRepository.delete(user2);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		
		return userRepository.findByEmail(email).orElseThrow(null);
	}

	@Override
	public User getUserByToken(String token) {
		
		return userRepository.findByEmailToken(token).orElse(null);
	}
  
}
