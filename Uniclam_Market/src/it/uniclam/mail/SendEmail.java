package it.uniclam.mail;

import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * Classe per l'invio di email all'utente
 * @author GiovanniTrovini
 *
 */
public class SendEmail {

	
	
	/**
	 * Metodo per inviare mail contenente le credenziali di accesso al sistema
	 * @param nome
	 * @param cognome
	 * @param telefono
	 * @param email
	 * @param cardnumber
	 * @param pin
	 * @throws AddressException
	 * @throws MessagingException
	 */
	
	public static void Email_User(Utente u, Scheda s) throws AddressException, MessagingException{
		
		
		String subject = "Registrazione Sistema Uniclam Market";

		String message2 = "Benvenuto nel nostro sistema, gentile "
				+ u.getNome()
				+ " "
				+ u.getCognome()
				+ " \n\n"
				+ "Le comunichiamo che la sua carta fedelta' e' stata attivata !!!"
				+ " \n\n"
				+ "RIEPILOGO DATI "
				+ " \n"
				+ "Dati anagrafici : "
				+ u.getNome()
				+ " "
				+ u.getCognome()
				+ "\n"
				+ "telefono : "
				+ u.getTelefono()
				+ "\n"
				+ "email : "
				+ u.getEmail()
				+ "\n\n"
				+"Le ricordiamo che il massimale da lei impostato e' di euro :  "+u.getMassimale()
				+"\n\n\n"
				+ "Ecco i suoi dati  da conservare per l'accesso al sistema "
				+ "\n\n" + "Numero Carta :  " + s.getIdScheda()
				+ "\nPin: " + s.getPin() + "\n\n"
				+ "Saluti - Uniclam Market ";

		EmailUtility.sendEmail(EmailUtility.HOST,
				EmailUtility.PASSWORD, EmailUtility.USER,
				EmailUtility.PASSWORD, u.getEmail(), subject, message2);
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
