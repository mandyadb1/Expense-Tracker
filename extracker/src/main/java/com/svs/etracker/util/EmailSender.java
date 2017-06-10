package com.svs.etracker.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class EmailSender {

	@Value("${excelFilePath}")
	private String excelFilePath;

	@Value("${fromEmail}")
	private String fromEmail;

	@Value("${fromEmailPassword}")
	private String fromEmailPassword;

	public  void sendEmailWithAttachments(String toAddress,
			String subject, String message)
					throws AddressException, MessagingException {
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", fromEmail);
		properties.put("mail.password", fromEmailPassword);

		String[] attachFiles = new String[1];
		attachFiles[0] = excelFilePath;

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, fromEmailPassword);
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(fromEmail));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachFiles != null && attachFiles.length >= 0) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}
		}

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);

	}

	/**
	 * Test sending e-mail with attachments
	 */
	/*public static void main(String[] args) {
		// SMTP info
		String host = "smtp.gmail.com";
		String port = "587";
		String mailFrom = "your-email-address";
		String password = "your-email-password";

		// message info
		String mailTo = "your-friend-email";
		String subject = "New email with attachments";
		String message = "I have some attachments for you.";

		// attachments
		String[] attachFiles = new String[3];
		attachFiles[0] = "e:/Test/Picture.png";
		attachFiles[1] = "e:/Test/Music.mp3";
		attachFiles[2] = "e:/Test/Video.mp4";

		try {
			sendEmailWithAttachments(mailTo,
					subject, message);
			System.out.println("Email sent.");
		} catch (Exception ex) {
			System.out.println("Could not send email.");
			ex.printStackTrace();
		}*/
}

