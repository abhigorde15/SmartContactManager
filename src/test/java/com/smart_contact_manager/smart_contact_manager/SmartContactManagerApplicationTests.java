package com.smart_contact_manager.smart_contact_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smart_contact_manager.smart_contact_manager.service.EmailService;

@SpringBootTest
class SmartContactManagerApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private EmailService service;
	
	
	@Test
	void sendEmailTest() {
		service.sendEmail("abhishekgorde777@gmail.com",
				"Just testing email",
				"this is testing of emailservice");
	}

}
