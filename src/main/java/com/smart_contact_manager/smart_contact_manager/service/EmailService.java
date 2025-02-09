package com.smart_contact_manager.smart_contact_manager.service;

public interface EmailService {
     
	void sendEmail(String to,String subject,String body);
	
	void sendEmailWithHtml();
	
	void sendEmailWithAttchment();
}
