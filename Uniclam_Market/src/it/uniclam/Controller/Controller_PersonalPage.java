package it.uniclam.Controller;

 import it.uniclam.GUI.Login_GUI;
 import it.uniclam.GUI.Spesa_GUI;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;
import it.uniclam.entity.Utente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
 
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Controller Pagina Personale
 * @author Giovanni Trovini
 *
 */
public class Controller_PersonalPage {

	static Icon scary = new ImageIcon("img/scary.png");
	static Icon error = new ImageIcon("img/error.png");
	static Icon email_icon = new ImageIcon("img/email.png");


	/**
	 * Permette all'utente di accede all'area della spesa. 
	 * @param s     Socket
	 * @param card  Scheda
	 */
	public static void effettuaspesa(Socket s, Scheda card) {

		try {

			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));

			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			// preparo la richiesta da inviare al server
			String req = Server.CREATE_SHOPPING + "/" + card.getIdScheda()+"\n";

			System.out.println("\n"+req);

			// invio richiesta al server
			Login_GUI.out.println(req);

			String line = Login_GUI.in.readLine();

			String parts[] = line.split("/");

			String operation = parts[0];


			Login_GUI.out.println(line);
			System.out.println(line);

			if (operation.contentEquals(Server.SHOPPING_CREATED)) {


				Spesa shop=new Spesa(Integer.parseInt(parts[1]));

				Spesa_GUI spesawindow = new Spesa_GUI(s,shop,card);
				spesawindow.setVisible(true);

			}
		}

		catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}



	/**
	 * Eliminazione utente dal sistema
	 * @param s  Socket
	 * @param u  Utente
	 */
	public static void CancellaUtente(Socket s, Utente u) {

		try {

			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));

			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			//Preparo la richiesta da inviare al server
			String req = Server.DELETE_USER + "/" + u.getEmail()+"\n";

			System.out.println("\n"+req);

			//Invio la richiesta al server
			Login_GUI.out.println(req);


			//Ricevo la risposta dal server
			String line = Login_GUI.in.readLine();
			System.out.println(line);


		}

		catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Cambio mail di accesso
	 * @param s Socket
	 * @param u Utente
	 * @param new_mail Nuova mail da aggiornare
	 */
	public static void ChangeEmail(Socket s, Utente u, String new_mail) {

		// Pattern standard email 
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		//controllo se l'email inserita dall'utente, corrisponde al pattern
		if (new_mail.matches(emailPattern)) {
			try {

				Login_GUI.in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));

				Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

				// Preparo la richiesta da inviare al server
				String req = Server.CHANGE_EMAIL + "/" + u.getEmail() + "/" + new_mail+"\n";
				System.out.println("\n"+req);

				// Invio la richiesta al sever
				Login_GUI.out.println(req);

				// Ricevo la risposta dal server
				String line = Login_GUI.in.readLine();
				System.out.println(line);


				if (line.contentEquals(Server.EMAIL_CHANGED)) {

					JOptionPane.showMessageDialog(null,
							"Email modificata con successo ", "Email modificata", JOptionPane.INFORMATION_MESSAGE, email_icon);

				}

				else {
					JOptionPane.showMessageDialog(null,
							"Attenzione! Email non modificata!", "Errore", JOptionPane.INFORMATION_MESSAGE, error);

				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			JOptionPane
			.showMessageDialog(null, "Email inserita non valida !!!");
		}
	}


	/**
	 * Mostra i punti totali acquisiti precedentemente
	 * @param s Socket
	 * @param card Scheda
	  
	 */
	public static void  Show_punti_scheda(Socket s, Scheda card){
 

		try {

			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));

			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			//Preparo la richiesta da inviare al server
			String req = Server.SHOW_POINTS + "/" + card.getIdScheda()+"\n";

			System.out.println("\n"+req);
			//Invio la richiesta al server
			Login_GUI.out.println(req);


			//Ricevo la risposta dal server e la divido tramite uno split
			String line = Login_GUI.in.readLine();
			System.out.println(line);

			String parts[] = line.split("/");
			String operation=parts[0];

			if(operation.contentEquals(Server.POINTS_SHOWED)){

				card.setPunti_totali(Double.parseDouble(parts[1]));

			}
			else {
				System.out.println("Error");
			}

		}

		catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
 
	}

}
