package it.uniclam.Controller;

import it.uniclam.GUI.PersonalPage_GUI;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.Scheda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ControllerLogin {
	private static final int SYSTEM_MESSAGE = 0;
	public static BufferedReader in;
	public static PrintWriter out;
	public static Icon email_icon = new ImageIcon("img/email.png");
	public static Icon error_icon = new ImageIcon("img/error.png");
	
	public static void authenticate(String numscheda, String pino) {

		try {

			if (ControllerLogin.in == null || ControllerLogin.out == null) {
				// apro socket 
				Socket s = new Socket("localhost", 8888);

				in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				out = new PrintWriter(s.getOutputStream(), true);

				// Invio la richiesta al server

				String req = Server.LOGIN_UTENTE + "/" + numscheda + "/" + pino
						+ "\n";
				System.out.println(req);
				
				out.println(req);
				String line = in.readLine();

				int numcard = Integer.parseInt(numscheda);
				int pin = Integer.parseInt(pino);

				String parts[] = line.split("/");

				if (parts[0].contentEquals("login_Ok")) {
					Scheda card = new Scheda(numcard, pin);

					double mas_res = Double.parseDouble(parts[1]);

					String nome = parts[2];
					String cognome = parts[3];
					String email = parts[4];

					PersonalPage_GUI personalwindow = new PersonalPage_GUI(
							card.getIdScheda(), card.getPin(), s, mas_res,
							nome, cognome, email);
					personalwindow.setVisible(true);
					

				} else {
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



	public static void forgot_pin(String email){

		try {

			if (ControllerLogin.in == null || ControllerLogin.out == null) {
				// apro socket
				Socket s = new Socket("localhost", 8888);

				in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				out = new PrintWriter(s.getOutputStream(), true);

				// Invio la richiesta al server

				String req = Server.RECOVERY_PIN + "/"+email ; 
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

			}
		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}





	}
}
