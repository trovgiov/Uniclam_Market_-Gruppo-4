package it.uniclam.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.uniclam.GUI.Login_GUI;
import it.uniclam.GUI.Spesa_GUI;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.JTableOperation;

public class ControllerSpesa {

	private static final int WARNING_MESSAGE = 0;

	public static void cancellaSpesa(Socket s, int idSpesa) throws IOException {

		Login_GUI.in = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

		String req = Server.CANCELLA_SPESA + "/" + idSpesa;
		Login_GUI.out.println(req);

		String line = Login_GUI.in.readLine();

		if (line.contentEquals(Server.SPESA_CANCELLATA)) {

			JOptionPane.showMessageDialog(null, "Spesa Eliminata dal sistema. Arrivederci!");

		} else {
			JOptionPane.showMessageDialog(null,
					"Attenzione! Spesa non cancellata!");

		}

	}

	/**
	 * Metodo che aggiunge il prodotto e restituisce l'importo (costo *
	 * quantità)
	 * 
	 * @param s
	 * @param barcode
	 * @param quantita
	 * @param idspesa
	 * @param table
	 * @return
	 */
	public static double AddProduct(Socket s, String barcode, String quantita,
			int idspesa, JTable table) {

		double importo_finale = 0;
		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			String response = Server.INSERT_PRODUCTS + "/" + barcode + "/"
					+ idspesa + "/" + quantita;

			Login_GUI.out.println(response);

			String line = Login_GUI.in.readLine();
			String part[] = line.split("/");

			if (part[0].contentEquals("prodotto inserito")) {

				try {
					DefaultTableModel dm = new JTableOperation()
					.getData(idspesa);

					// dm=SpesaDAOImpl.getInstance().getData(idspesa);

					table.setModel(dm);

					importo_finale = Double.parseDouble(part[1]);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Prodotto non inserito", "ATTENZIONE", WARNING_MESSAGE, null);

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return importo_finale;

	}

	/**
	 * Metodo per eliminare il prodotto dalla lista della spesa. Restitusce
	 * l'importo finale detratto
	 * 
	 * @param s
	 * @param barcode
	 * @param quantita
	 * @param idspesa
	 * @param table
	 * @return
	 */
	public static double DeleteProduct(Socket s, String barcode, int idspesa,
			JTable table) {
		double importo_finale = 0;

		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			String response = Server.DELETE_PRODUCTS + "/" + barcode + "/"
					+ idspesa;

			Login_GUI.out.println(response);

			String line = Login_GUI.in.readLine();
			String[] part = line.split("/");

			if (part[0].contentEquals("prodotto eliminato")) {

				try {
					DefaultTableModel dm = new JTableOperation()
					.getData(idspesa);

					// dm=SpesaDAOImpl.getInstance().getData(idspesa);

					table.setModel(dm);

					importo_finale = Double.parseDouble(part[1]);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "prodotto non eliminato");

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return importo_finale;

	}

	public static double UpdateProduct(Socket s, String barcode,
			String quantita, int idspesa, JTable table) {

		double importo_finale = 0;
		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			String response = Server.UPDATE_PRODUCTS + "/" + barcode + "/"
					+ quantita + "/" + idspesa;

			Login_GUI.out.println(response);

			String line = Login_GUI.in.readLine();
			String[] part = line.split("/");

			if (part[0].contentEquals("prodotto aggiornato")) {

				try {
					DefaultTableModel dm = new JTableOperation()
					.getData(idspesa);

					// dm=SpesaDAOImpl.getInstance().getData(idspesa);

					table.setModel(dm);

					importo_finale = Double.parseDouble(part[1]);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "prodotto non aggiornato");

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return importo_finale;
	}



	public static String getSpesa(Socket s,int idspesa){

		String error="errore";
		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			String response = Server.SHOW_SPESA + "/" + idspesa ;
			System.out.println(response);

			Login_GUI.out.println(response);

			String line = Login_GUI.in.readLine();
			String[] part = line.split("/");

			if (part[0].contentEquals(Server.SPESA_INVIATA)) {

				JOptionPane.showMessageDialog(null, part[1]);

				return part[1];


			}

			else {
				JOptionPane.showMessageDialog(null, "prodotto non eliminato");

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return error;



	}


	public static void UpdateMassimale(Socket s,double up_mass,int idspesa){

		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

			String response = Server.UPDATE_MASSIMALE + "/"+up_mass+"/"+idspesa;

			Login_GUI.out.println(response);

			System.out.println(response);





			String line = Login_GUI.in.readLine();

			if (line.contentEquals(Server.MASSIMALE_INSERITO)) {

				System.out.println("Massimale aggiornato");

			}
			else{
				System.out.println("Massimale non aggiornato");

			}

		}
		catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}







	}



	public static int  CalcolaPuntiSpesa(Socket s , int idspesa){
	
		int punti_spesa=0;
		try {
			Login_GUI.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			Login_GUI.out = new PrintWriter(s.getOutputStream(), true);


			String response = Server.GET_POINTS + "/"+idspesa;

			Login_GUI.out.println(response);


			System.out.println(response);





			String line = Login_GUI.in.readLine();


			String part[] = line.split("/");

			if (part[0].contentEquals(Server.ADD_POINTS)) {

				
				 punti_spesa=Integer.parseInt(part[1]);
			 
			}

			else {
				JOptionPane.showMessageDialog(null, "Punti non aggiornati", "ATTENZIONE", WARNING_MESSAGE, null);

			}

		} catch (IOException ioe) {

			JOptionPane.showMessageDialog(null,
					"Error in communication with server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return punti_spesa;

	}



public static int CalcoloPuntiTotali(Socket s,int idspesa,int punti_spesa){
	
	int punti_spesa_aggiornati=0;
	
	try {
		Login_GUI.in = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		Login_GUI.out = new PrintWriter(s.getOutputStream(), true);


		String response = Server.UPDATE_POINTS + "/"+idspesa+"/"+punti_spesa;

		Login_GUI.out.println(response);


		System.out.println(response);





		String line = Login_GUI.in.readLine();


		String part[] = line.split("/");

		if (part[0].contentEquals(Server.POINTS_UPDATED)) {

			
			 punti_spesa_aggiornati=Integer.parseInt(part[1]);
		 
		}

		else {
			JOptionPane.showMessageDialog(null, "Punti non aggiornati", "ATTENZIONE", WARNING_MESSAGE, null);

		}

	} catch (IOException ioe) {

		JOptionPane.showMessageDialog(null,
				"Error in communication with server!", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	return punti_spesa_aggiornati;

}
	

public static void updateMassimale_totale(Socket s,int idspesa, double update_massres){
	try {
		Login_GUI.in = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		Login_GUI.out = new PrintWriter(s.getOutputStream(), true);


		String response = Server.UPDATE_MASSIMALE_RESIDUO + "/"+idspesa+"/"+update_massres+"/"  +"\n";

		Login_GUI.out.println(response);


		System.out.println(response);





		String line = Login_GUI.in.readLine();

		System.out.println(line);


		if (line.contentEquals(Server.MASSIMALE_RESIDUO_UPDATED)) {
			
			


			JOptionPane.showMessageDialog(null, "Massimale aggiornato");

		}

		else {
			JOptionPane.showMessageDialog(null, "Punti non aggiornati", "ATTENZIONE", WARNING_MESSAGE, null);

		}

	} catch (IOException ioe) {

		JOptionPane.showMessageDialog(null,
				"Error in communication with server!", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	
	
	
	
	
	
}

 
}