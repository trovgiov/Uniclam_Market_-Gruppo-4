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
}
