package it.uniclam.Controller;

import it.uniclam.DAO.UtenteDAOImpl;
import it.uniclam.GUI.Login_GUI;
import it.uniclam.GUI.PersonalPage_GUI;
import it.uniclam.GUI.Spesa_GUI;
import it.uniclam.UniclamMarket.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Controller_PersonalPage {
	
	static Icon scary = new ImageIcon("img/scary.png");
 	static Icon error = new ImageIcon("img/error.png");
 	static Icon email_icon = new ImageIcon("img/email.png");
 	
	public static void effettuaspesa(Socket s, int scheda, double mass_res) {

		try {

			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));

			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			String req = Server.CREA_SPESA + "/" + scheda;
			System.out.println(req);

			Login_GUI.out.println(req);

			String line = Login_GUI.in.readLine();

			String parts[] = line.split("/");

			String operation = parts[0];
			String id_spesa = parts[1];
			int idspesa = Integer.parseInt(id_spesa);

			Login_GUI.out.println(line);

			if (operation.contentEquals("spesa_creata")) {

				Spesa_GUI spesawindow = new Spesa_GUI(idspesa, s, mass_res);
				spesawindow.setVisible(true);

			}
		}

		catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void CancellaUtente(Socket s, String email) {

		try {

			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));

			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			String req = Server.ELIMINAUTENTE + "/" + email;
			System.out.println(req);

			Login_GUI.out.println(req);

			String line = Login_GUI.in.readLine();

			if (line.contentEquals(Server.UTENTE_ELIMINATO)) {
				System.out.println("Utente cancellato correttamente!");

			} else {
				System.out.println("ATTENZIONE Utente non eliminato!");

			}

			// Login_GUI.out.println(line);

		}

		catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void ChangeEmail(Socket s, String email, String new_mail) {

		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (new_mail.matches(emailPattern)) {
			try {

				Login_GUI.in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));

				Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

				String req = Server.CHANGE_EMAIL + "/" + email + "/" + new_mail;
				System.out.println(req);

				Login_GUI.out.println(req);

				String line = Login_GUI.in.readLine();

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
	
	
	public static int  Show_punti_scheda(Socket s, int idscheda){
		int punti=0;
		
		
		try {

			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));

			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			String req = Server.SHOW_POINTS + "/" + idscheda;
			System.out.println(req);

			Login_GUI.out.println(req);

			String line = Login_GUI.in.readLine();
			String parts[] = line.split("/");
			String operation=parts[0];
			
			if(operation.contentEquals(Server.POINTS_SHOWED)){
				
				 punti=Integer.parseInt(parts[1]);
				
				
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
		return punti;

	}

 }
