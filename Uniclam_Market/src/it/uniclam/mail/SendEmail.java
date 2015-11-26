package it.uniclam.mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
 

public class SendEmail {

	
	static String host="smtp.gmail.com";
	static String port="587";
	static String userName="trovgiov@gmail.com";
	static String password="clapton91";
	static String email="eneamarinelli@gmail.com";
	static String subject="Oggetto";
	static String message="Messaggio ciao stronzo";
	
	
	
	public static void main (String arg[]) throws AddressException, MessagingException{
		EmailUtility.sendEmail(host, port, userName, password,
				email, subject, message);
		
		
		
		
		
		
		
	}
	 //public static void main(String arg[]) throws AddressException, MessagingException{
		/* 
		 EmailUtility.sendEmail(host, port, userName, password,
					email, subject, message);
	 */
	 //}
}
