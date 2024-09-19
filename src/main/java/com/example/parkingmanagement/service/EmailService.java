package com.example.parkingmanagement.service;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(String to, String subject, String body) throws MessagingException {
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage());
		messageHelper.setTo(to);
		messageHelper.setSubject(subject);
		messageHelper.setText(body);

		mailSender.send(messageHelper.getMimeMessage());
	}

	public void sendNotificationEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}

}
