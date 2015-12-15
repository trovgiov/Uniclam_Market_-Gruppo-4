package it.uniclam.Controller;

import it.uniclam.GUI.Login_GUI;
import it.uniclam.GUI.PersonalPage_GUI;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.Scheda;
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
 * Controller Login Utente
 * @author GiovanniTrovini
 *
 */
public class ControllerLogin {
	private static final int SYSTEM_MESSAGE = 0;
	public static BufferedReader in;
	public static PrintWriter out;
	public static Icon email_icon = new ImageIcon("img/email.png");
	public static Icon error_icon = new ImageIcon("img/error.png");



	/**
	 * Permette l'autenticazione dell'utente
	 * @param card  Scheda
	 */
	public static void authenticate(Scheda card) {

		try {

			if (ControllerLogin.in == null || ControllerLogin.out == null) {
				// apro socket 
				Socket s = new Socket(Server.HOST, Server.port);

				in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				out = new PrintWriter(s.getOutputStream(), true);

				// Invio la richiesta al server

				String req = Server.LOGIN_USER + "/" + card.getIdScheda() + "/" + card.getPin()
						+ "\n";

				System.out.println(req);
				out.println(req);





				String line = in.readLine();

				System.out.println(line);

				String parts[] = line.split("/");

				if (parts[0].contentEquals(Server.USER_LOGGED)) {

					double mas_res = Double.parseDouble(parts[1]);

					card.setMassimale_res(mas_res);
					Utente u=new Utente(parts[2],parts[3],parts[4]);


					PersonalPage_GUI personalwindow = new PersonalPage_GUI(
							s,card,u);

					personalwindow.setVisible(true);


				} else if(parts[0].contentEquals(Server.LOGIN_ERROR)){
					JOptionPane.showMessageDialog(null, "Login Errato! \nSe non ricordi i dati puoi usare il tasto di recupero", 
							"Attenzione!", JOptionPane.ERROR_MESSAGE, error_icon);
				}

			}
		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}


	/**
	 * Consente il  recupero di id scheda e pin, tramite l'invio di una mail.
	 * @param u Utente
	 */
	public static void forgot_pin(Utente u){

		try {

			if (ControllerLogin.in == null || ControllerLogin.out == null) {
				// apro socket
				Socket s = new Socket(Server.HOST, Server.port);

				in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				out = new PrintWriter(s.getOutputStream(), true);

				// Invio la richiesta al server

				String req = Server.RECOVERY_PIN + "/"+u.getEmail()+"\n" ; 
				System.out.println (req);
				out.println(req);

				String line = in.readLine();
				System.out.println(line);


				if(line.contentEquals(Server.PIN_RECOVERED)){

					JOptionPane.showMessageDialog(null, "L'email inserita è presente nel sistema.\nA breve riceverà una mail con i dati necessari per l'accesso", 
							"Email inviata con successo", SYSTEM_MESSAGE, email_icon);

				}
				else{
					JOptionPane.showMessageDialog(null, "L'email non è presente nel sistema! \nInserisci l'email usata per il primo accesso", 
							"Email non inviata", JOptionPane.ERROR_MESSAGE, error_icon);
				}
				s.close();
			}
		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}





	}


	// Da Fare
	public static void logout( ) throws IOException{


		//Preparo la richiesta da inviare al server
		String req = Server.LOGOUT;
		Login_GUI.out.println(req);

		System.out.println(req);

		Login_GUI.in.close();
		Login_GUI.out.close();
		//Invio la richiesta






		System.out.println("Socket Closed");			
	}

}



