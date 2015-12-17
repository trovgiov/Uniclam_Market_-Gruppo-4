package it.uniclam.Controller;

import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;
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

/**
 * Controller Utente
 * @author GiovanniTrovini
 *
 */
public class ControllerUtente {

	private static final int SYSTEM_MESSAGE = 0;

	/**
	 * Consente la registrazione dell'utente con l'inserimento di campi obbligatori.
	 * @param u Utente
	 * @param emailPattern Pattern per evitare errori 
	 */
	public static void registrazioneUtente(Utente u, String emailPattern) {

		if (u.getEmail().matches(emailPattern) && u.getNome().length() != 0
				&& u.getCognome().length() != 0 && u.getTelefono().length() != 0
				&& u.getMassimale() != 0) {
			try {
				Socket s = new Socket(Server.HOST, Server.port);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);

				String req = Server.INSERT_USER + "/" + u.getNome() + "/" + u.getCognome()
						+ "/" + u.getEmail() + "/" + u.getTelefono() + "/" + u.getMassimale() +"\n";


				out.println(req);

				System.out.println("\n"+req);

				//	Icon happy = new ImageIcon("img/happy.png");

				// ricevo la richiesta dal server e con il metodo split, divido la stringa 
				String line = in.readLine();
				String parts[] = line.split("/");

				String response = parts[0];





				if (response.contentEquals(Server.USER_INSERTED)) {

					System.out.println(response);
					Icon happy = new ImageIcon("img/happy.png");

					JOptionPane.showMessageDialog(null, "Grazie per esserti registrato! La tua carta è stata attivata."
							+ "\nA breve riceverai una mail con il numero carta ed il pin, necessario per l'accesso."
							, "Esito registrazione OK", SYSTEM_MESSAGE, happy);


					// Creo Scheda con id scheda e pin
					//parts[1]=numero scheda
					// parts[2]=pin

					Scheda card = new Scheda(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));	



					// Dati necessari per l'invio mail

					SendEmail.Email_User(u, card);


 				}

				else {
					System.out.println(response);

					Icon error = new ImageIcon("img/error.png");
					JOptionPane.showMessageDialog(null, "Errore! Qualche campo risulta non essere corretto!"
							, "Errore", SYSTEM_MESSAGE, error);
				}

				s.close();
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
