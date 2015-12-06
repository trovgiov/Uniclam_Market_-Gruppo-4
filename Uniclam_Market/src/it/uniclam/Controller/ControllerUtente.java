package it.uniclam.Controller;

import it.uniclam.UniclamMarket.Server;
import it.uniclam.mail.EmailUtility;
import it.uniclam.mail.SendEmail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;

public class ControllerUtente {

	public static void registrazioneUtente(String nome, String cognome,
			String email, String massimale, String telefono, String emailPattern) {

		if (email.matches(emailPattern) && nome.length() != 0
				&& cognome.length() != 0 && telefono.length() != 0
				&& massimale.length() != 0) {
			try {
				Socket s = new Socket("localhost", 8888);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);

				String req = Server.INSERT_UTENTE + "/" + nome + "/" + cognome
						+ "/" + email + "/" + telefono + "/" + massimale + "\n";

				out.println(req);
				System.out.println("Email valida");
				System.out.println("Inviato: " + req);

				String line = in.readLine();
				String parts[] = line.split("/");
				String message = parts[0];
				String pin = parts[2];
				String cardnumber = parts[1];
				System.out.println(line);

				if (message.contentEquals("OK")) {
					JOptionPane
							.showMessageDialog(
									null,
									"Complimenti. La sua carta è stata attivata."
											+ "\nA breve riceverà una mail con il numero carta ed il pin, necessario per l'accesso."
											+ "\nUniclam Market");

				
					
					
					SendEmail.Email_User(nome, cognome, telefono, email, cardnumber, pin);
					 

				}

				else {
					JOptionPane.showMessageDialog(null,
							"Errore inserimento campi");
				}

			} catch (IOException ioe) {

				JOptionPane.showMessageDialog(null,
						"Error in communication with server!", "Error",
						JOptionPane.ERROR_MESSAGE);
			 
			}
		catch(AddressException a)	{
			
			JOptionPane.showMessageDialog(null,
					"Error with email address", "Error",
					JOptionPane.ERROR_MESSAGE);
			
			
			
		}
			
			catch(MessagingException m){
				JOptionPane.showMessageDialog(null,
						"Error with mail message", "Error",
						JOptionPane.ERROR_MESSAGE);
				
				
				
			}
			}
		
		}
		
	
	
	
	

}
