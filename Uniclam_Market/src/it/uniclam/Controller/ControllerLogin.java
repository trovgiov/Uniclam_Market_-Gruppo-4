package it.uniclam.Controller;

import it.uniclam.GUI.PersonalPage_GUI;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.Scheda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ControllerLogin {
	public static BufferedReader in;
	public static PrintWriter out;
	
	
	public static void authenticate(String numscheda,String pino){
		
		
		try {

			if (ControllerLogin.in == null || ControllerLogin.out == null) {
				// apro socket
				Socket s = new Socket("localhost", 8888);

				in = new BufferedReader(new InputStreamReader(s
						.getInputStream()));
				out = new PrintWriter(s.getOutputStream(), true);

				 
				
				// Invio la richiesta al server
				
				String req = Server.LOGIN_UTENTE + "/"+numscheda
						+ "/"+pino + "\n";
				System.out.println("Richiesta "+req);
				out.println(req);
				String line = in.readLine();


				int numcard=Integer.parseInt(numscheda);
				int pin=Integer.parseInt(pino);
				
				if (line.contentEquals("login_Ok")) {
					Scheda card = new Scheda(numcard, pin);

					PersonalPage_GUI personalwindow = new PersonalPage_GUI(
							card.getIdScheda(), card.getPin(),s);
					personalwindow.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null, "Login Errato");
				}

			}
		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		
		
	}
}
