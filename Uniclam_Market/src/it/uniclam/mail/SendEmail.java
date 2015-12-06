package it.uniclam.mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class SendEmail {

	
	
	/**
	 * Metodo per iviare mail contenente le credenziali di accesso al sistema
	 * @param nome
	 * @param cognome
	 * @param telefono
	 * @param email
	 * @param cardnumber
	 * @param pin
	 * @throws AddressException
	 * @throws MessagingException
	 */
	
	public static void Email_User(String nome,String cognome,String telefono,String email,String cardnumber,String pin) throws AddressException, MessagingException{
		
		
		String subject = "Registrazione Sistema Uniclam Market";

		String message2 = "Benvenuto nel nostro sistema, gentile "
				+ nome
				+ " "
				+ cognome
				+ " \n\n"
				+ "Le comunichiamo che la sua carta fedelta' e' stata attivata !!!"
				+ " \n\n"
				+ "Riepilogo Dati: "
				+ " \n"
				+ "Dati anagrafici : "
				+ nome
				+ " "
				+ cognome
				+ "\n"
				+ "telefono : "
				+ telefono
				+ "\n"
				+ "email : "
				+ email
				+ "\n\n"
				+ "Ecco i suoi dati di accesso da conservare per l'accesso al sistema "
				+ "\n" + "Numero Carta :  " + cardnumber
				+ "\nPin: " + pin + ".\n\n"
				+ "Saluti - Uniclam Market ";

		EmailUtility.sendEmail(EmailUtility.HOST,
				EmailUtility.PASSWORD, EmailUtility.USER,
				EmailUtility.PASSWORD, email, subject, message2);
	}
	
	
	
	
	/**
	 * Metodo per l'invio mail di recupero credenziali di accesso. Il metodo viene invocato quando si fa click su recupera pin e si inserisce la mail per il recupero.
	 * @param idscheda
	 * @param pin
	 * @param email
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void Email_Recovery(int idscheda,int pin,String email) throws AddressException, MessagingException{
		
		
		String oggetto = "Recupero Numero Scheda e Pin - Uniclam Market";
		String message_recovery = "Salve, come da lei richiesto, ecco i dati necessari per il recupero dei dati di accesso al sistema Uniclam market"
				+ "\n\n"
				+ "Numero Carta "
				+ idscheda
				+ "\n"
				+ "Pin : "
				+ pin
				+ "\n\n"
				+ "Se non ha richiesto il recupero del pin, ignori questa mail."
				+ "\n\n" + "Saluti - Uniclam Market ";

		 
			EmailUtility.sendEmail(EmailUtility.HOST, EmailUtility.PORT,
					EmailUtility.USER, EmailUtility.PASSWORD, email,
					oggetto, message_recovery);
		 
	}

 
	
	
}
