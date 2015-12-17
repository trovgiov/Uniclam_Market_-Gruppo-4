package it.uniclam.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.uniclam.GUI.Login_GUI;
import it.uniclam.GUI.PersonalPage_GUI;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.Carrello;
import it.uniclam.entity.JTableOperation;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;
import it.uniclam.entity.Utente;


/**
 * Controller  spesa
 * @author GiovanniTrovini
 *
 */
public class ControllerSpesa {

	static Icon warning = new ImageIcon("img/error.png");
	static Icon ok = new ImageIcon("img/happy.png");


	/**
	 * Consente l'annullamento della spesa e la relativa cancellazione nel db.
	 * @param s Socket
	 * @param shop Spesa
	 * @throws IOException
	 */
	public static void cancellaSpesa(Socket s,Spesa shop) throws IOException {

		Login_GUI.in = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

		//Preparo la richiesta da mandare al server
		String req = Server.DELETE_SHOP+"\n";
		System.out.println("\n"+req);

		//invio la richiesta
		Login_GUI.out.println(req);


		String line = Login_GUI.in.readLine();
		System.out.println("\n"+line);


		if (line.contentEquals(Server.SHOP_DELETED)) {

			JOptionPane.showMessageDialog(null, "Spesa Eliminata dal sistema!", "Conferma eliminazione", JOptionPane.CLOSED_OPTION, ok);

		} else {
			JOptionPane.showMessageDialog(null,
					"Attenzione! Spesa non cancellata!");

		}

	}

	/**
	 * Aggiunge i prodotti e calcola l'importo totale SUM(costo *
	 * quantità)
	 * Ogni prodotto viene inserito nella JTable
	 * @param s Socket
	 * @param barcode Codice a barre prodotto
	 * @param quantita Quantità prodotto
	 * @param idspesa  Id della spesa
	 * @param table    JTable
	 */
	public static void AddProduct(Socket s,Carrello c , Spesa shop, JTable table) {

		double importo_finale = 0;
		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			//Preparo la richiesta da inviare al server
			String req = Server.INSERT_PRODUCT + "/" + c.getBarcode() + "/"
					+ c.getQuantita()+"\n";

			System.out.println("\n"+req);

			//invio la richiesta
			Login_GUI.out.println(req);


			String line = Login_GUI.in.readLine();
			System.out.println(line);

			String part[] = line.split("/");

			if (part[0].contentEquals(Server.PRODUCT_INSERTED)) {

				try {
					DefaultTableModel dm = new JTableOperation()
					.getData(shop.getIdspesa());


					table.setModel(dm);

					importo_finale = Double.parseDouble(part[1]);
					shop.setImporto_tot(importo_finale); 

				} catch (SQLException e1) {
					e1.printStackTrace();

				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Prodotto non inserito!!", "ATTENZIONE", JOptionPane.WARNING_MESSAGE, warning);

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}


	}

	/**
	 * Elimina il prodotto dalla lista della spesa.  
	 * Calcola l'importo finale detratto.
	 * 
	 * @param s Socket
	 * @param barcode Codice a barre prodotto
	 * @param quantita Quantità prodotto
	 * @param idspesa  Id della spesa
	 * @param table    JTable

	 */
	public static void DeleteProduct(Socket s, Carrello c, Spesa shop,
			JTable table) {

		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			//Preparo la richiesta da inviare al server
			String response = Server.DELETE_PRODUCT + "/" + c.getBarcode() + "\n";

			System.out.println("\n"+response);
			//Invio la richiesta al server
			Login_GUI.out.println(response);

			String line = Login_GUI.in.readLine();
			System.out.println(line);

			String[] part = line.split("/");

			if (part[0].contentEquals(Server.PRODUCT_DELETED)) {

				try {
					DefaultTableModel dm = new JTableOperation()
					.getData(shop.getIdspesa());


					table.setModel(dm);

					// importo finale = Double.parseDouble(part[1]);
					shop.setImporto_tot(Double.parseDouble(part[1])); 


				} catch (SQLException e1) {
					e1.printStackTrace();

				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Prodotto non eliminato!!", "ATTENZIONE", JOptionPane.WARNING_MESSAGE, warning);

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}


	}

	/**
	 *  Aggiorna la quantità del prodotto che si vuole acquistare.
	 *  Viene aggiornato l'importo finale
	 * @param s
	 * @param barcode
	 * @param quantita
	 * @param idspesa
	 * @param table
	 * @return

	 */
	public static void UpdateProduct(Socket s, Carrello c, Spesa shop, JTable table) {

		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			//Preparo la richiesta da inviare al server
			String req = Server.UPDATE_PRODUCT + "/" + c.getBarcode() + "/"
					+ c.getQuantita() + "\n";

			System.out.println("\n"+req);

			//Invio la richiesta al server
			Login_GUI.out.println(req);

			//Ricevo la richiesta dal client

			String line = Login_GUI.in.readLine();
			System.out.println(line);
			String[] part = line.split("/");

			if (part[0].contentEquals(Server.PRODUCT_UPDATED)) {

				try {
					DefaultTableModel dm = new JTableOperation()
					.getData(shop.getIdspesa());


					table.setModel(dm);

					shop.setImporto_tot(Double.parseDouble(part[1]));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Prodotto non aggiornato!!", "ATTENZIONE", JOptionPane.WARNING_MESSAGE, warning);

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}




	 

	/**
	 * Calcolo dei punti tessera fedeltà, per ogni singola spesa
	 * @param s Socket
	 * @param shop Spesa
	 */
	public static void  CalcolaPuntiSpesa(Socket s , Spesa shop){

		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			//Preparo la stringa da mandare al server
			String req = Server.GET_POINTS +"\n";

			System.out.println(req);

			//Invio la stringa al server
			Login_GUI.out.println(req);

			//Ricevo la risposta dal server 
			String line = Login_GUI.in.readLine();

			String parts[]=line.split("/");


			if (parts[0].contentEquals(Server.POINTS_ADDED)) {

				// assegno alla spesa, i relativi punti
				shop.setPunti_spesa(Integer.parseInt(parts[1]));


			}

			else {
				JOptionPane.showMessageDialog(null, "Punti non aggiornati!!", "ATTENZIONE", JOptionPane.WARNING_MESSAGE, warning);

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}


	/**
	 * Calcolo i punti totali.
	 * I punti totali sono dati dalla somma tra i punti acquisiti precedentemente e i punti acquisiti della spesa corrente.
	 * @param s Socket
	 * @param shop Spesa
	 * @param card Scheda
	 */
	public static void CalcoloPuntiTotali(Socket s,Spesa shop,Scheda card){


		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);


			//Preparo la richiesta da inviare al server
			String req = Server.UPDATE_POINTS + "/"+shop.getIdspesa()+"/"+shop.getPunti_spesa()+"\n";

			//Invio la richiesta al server
			Login_GUI.out.println(req);


			System.out.println(req);





			//ricevo la richiesta dal server
			String line = Login_GUI.in.readLine();

			System.out.println(line);
			String part[] = line.split("/");

			if (part[0].contentEquals(Server.POINTS_UPDATED)) {


				card.setPunti_totali(Double.parseDouble((part[1])));                 
			}

			else {

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}



	/**
	 * Aggiorna il massimale residuo con l'importo dell'ultima spesa.
	 * @param s
	 * @param shop
	 * @param card
	 */
	public static void updateMassimale_residuo(Socket s,Spesa shop, Scheda card,Utente u){
		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			//Preparo la richiesta da inviare al server
			String req = Server.UPDATE_MASSIMALE_RESIDUO + "/"+card.getMassimale_res()+"/"  +"\n";

			System.out.println(req);

			//Invio la richiesta
			Login_GUI.out.println(req);







			String line = Login_GUI.in.readLine();

			String part[]=line.split("/");
			System.out.println(line);


			if (part[0].contentEquals(Server.MASSIMALE_RESIDUO_UPDATED)) {


				card.setMassimale_res(Double.parseDouble(part[1]));
				
				PersonalPage_GUI windows=new PersonalPage_GUI(s, card, u);
				windows.setVisible(true);


			}

			else {
				JOptionPane.showMessageDialog(null, "Massimale non aggiornato. \nContattare : uniclamarket@gmail.comßå", "Attenzione", JOptionPane.WARNING_MESSAGE, warning);

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}






	}


}