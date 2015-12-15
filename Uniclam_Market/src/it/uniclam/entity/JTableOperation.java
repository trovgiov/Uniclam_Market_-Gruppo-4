package it.uniclam.entity;

import it.uniclam.db.DBUtility;

import java.sql.ResultSet;
import java.sql.SQLException;

 import javax.swing.table.DefaultTableModel;
/**
 * Creazione ed Interfacciamento con il db -   JTABLE
 * @author GiovanniTrovini
 *
 */


public class JTableOperation {
	//Costruttore
	public JTableOperation() {

	}

	/**
	 * Crea una Jtable contenente le informazioni riguardo l'inserimento,modifica o cancellazione del prodotto.
	 * La tabella si aggiorna dinamicamente, consentendo all'utente di vedere in tempo reale , i prodotti inseriri e l'importo finale
	 * @param idspesa INT
	 * @return DM DefaultTableModel
	 * @throws SQLException
	 */
	public DefaultTableModel getData(int idspesa) throws SQLException {

		// Aggiungo le colonne alla tabella

		DefaultTableModel dm = new DefaultTableModel();

		dm.addColumn("Prodotto");
		dm.addColumn("Prezzo");
		dm.addColumn("Quantita");

		// sql
		java.sql.Statement s = DBUtility.getStatement();

		// Query di select informazioni
		String sql = "select p.nome,c.quantita,p.costo from prodotto p, carrello c, spesa s where s.idspesa= '"
				+ idspesa
				+ "' and s.idspesa=c.spesa_idspesa and c.prodotto_barcode=p.barcode";

		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				String nome = rs.getString(1);
				int quantita_p = rs.getInt(2);

				Double costo_p = rs.getDouble(3);

				String costo = String.valueOf(costo_p);
				String quantita = String.valueOf(quantita_p);

				//Creazione righe con i dati provenienti dal db
				dm.addRow(new String[] { nome, costo, quantita });

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return dm;
	}





}