package com.smart_contact_manager.smart_contact_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
	private JavaMailSender emailSender;
    
    @Value("${spring.mail.properties.domain_name}")
    private String domainName;
	@Override
	public void sendEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		System.out.println("DomainName"+domainName);
		message.setFrom(domainName);
		emailSender.send(message);
		
	}

	@Override
	public void sendEmailWithHtml() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEmailWithAttchment() {
		// TODO Auto-generated method stub
		
	}

}
