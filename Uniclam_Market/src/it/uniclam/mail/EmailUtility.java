package it.uniclam.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Classe per la gestione dell'invio mail all'utente
 * 
 * @author Giovanni Trovini
 *
 */
public class EmailUtility {

	public static String HOST = "smtp.gmail.com";
	public static String PORT = "587";
	public static String USER = "uniclamarket@gmail.com";
	public static String PASSWORD = "trovinimarinelli";

	/**
	 * Configurazione parametri mail
	 * 
	 * @param host
	 * @param port
	 * @param userName
	 *            : the email of the sender
	 * @param password
	 * @param email
	 * @param subject
	 *            : the subject of the email
	 * @param message
	 *            : message
	 * @throws AddressException
	 * @throws MessagingException
	 */

	public static void sendEmail(String host, String port,
			final String userName, final String password, String email,
			String subject, String message) throws AddressException,
			MessagingException {
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(email) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setContent(message, "text/plain");

		msg.setContent(message, "text/plain");

		// sends the e-mail
		Transport.send(msg);

	}
}