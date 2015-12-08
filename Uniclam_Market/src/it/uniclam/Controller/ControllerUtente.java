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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ControllerUtente {

	private static final int SYSTEM_MESSAGE = 0;

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

				Icon happy = new ImageIcon("img/happy.png");
				String line = in.readLine();
				String parts[] = line.split("/");
				String message = parts[0];
				String pin = parts[2];
				String cardnumber = parts[1];
				System.out.println(line);

				if (message.contentEquals("OK")) {
					JOptionPane.showMessageDialog(null, "Grazie per esserti registrato! La tua carta è stata attivata."
							+ "\nA breve riceverai una mail con il numero carta ed il pin, necessario per l'accesso."
							, "Esito registrazione OK", SYSTEM_MESSAGE, happy);
									
									

				
					
					
					SendEmail.Email_User(nome, cognome, telefono, email, cardnumber, pin);
					 

				}

				else {
					Icon error = new ImageIcon("img/error.png");
					JOptionPane.showMessageDialog(null, "Errore! Qualche campo risulta non essere corretto!"
							, "Errore", SYSTEM_MESSAGE, error);
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
