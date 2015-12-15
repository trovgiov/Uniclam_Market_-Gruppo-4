package it.uniclam.DAO;

import it.uniclam.db.DBUtility;
import it.uniclam.entity.Carrello;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;

import java.sql.ResultSet;
import java.sql.SQLException;
 
import java.util.Random;

 import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

/**
 * Implementazione metodi interfaccia Spesa DAO
 * @author GiovanniTrovini
 *
 */
public class SpesaDAOImpl implements SpesaDAO {

	private SpesaDAOImpl() {
	}

	private static SpesaDAO dao = null;

	public static SpesaDAO getInstance() {
		if (dao == null) {
			dao = new SpesaDAOImpl();
		}
		return dao;
	}

 

	/**
	 * Inserisce la spesa nel database,generando un id spesa con la funzione random
	 * @param  c Entity Spesa  
	 * @return id spesa
	 * @throws SQLException
	 */
	@Override
	 public int insertSpesa(Spesa c) throws SQLException {

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// genero un id spesa con il metodo random
		Random random = new Random();
		int j = 10;
		int n = 70 - j;
		int idspesa = random.nextInt(n) + j;

		// Query per l'inserimento nella tabella spesa 
		String insertTableSQL = "insert into spesa (idspesa,scheda_idScheda,data_spesa) VALUES (?,?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, idspesa);

			preparedStatement.setInt(2, c.getIdscheda());
			preparedStatement.setDate(3, (java.sql.Date) c.getData_spesa());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Inserimento Data Spesa Effettuato");

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			throw e;

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return idspesa;

	}

	 
	/**
	 * Aggiunge i  prodotti nel carrello, tramite una query di insert nel database.
	 * Restituisce una variabile boolean. Se return true allora il prodotto è stato correttamente inserito
	 * @param  c Entity Carrello
	 * @param  shop Entity Spesa
	 * @return
	 * @throws SQLException
	 */
	@Override
	public boolean addProducts(Carrello c , Spesa shop)
			throws SQLException {

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		String insertTableSQL = "insert into carrello(prodotto_barcode,spesa_idSpesa,quantita)  VALUES ('"
				+ c.getBarcode() + "','" + shop.getIdspesa() + "', '" + c.getQuantita() + "')";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			// execute insert SQL stetement
			preparedStatement.execute(insertTableSQL);

			return true;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

			return false;

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	/**
	 * Metodo necessario per l'interfacciamento con la JTable. Esegue una select di tutte le informazioni necessarie sui prodotti, che saranno fornite all'utente
	 * @param  int idspesa
	 * @return DefaultTableModel dm
	 */
	 
	@Override
	public DefaultTableModel getData(int idspesa) throws SQLException {

		// Aggiungo le colonne alla tabella

		DefaultTableModel dm = new DefaultTableModel();

		dm.addColumn("Barcode");
		dm.addColumn("Prezzo");
		dm.addColumn("Quantita");

		// sql
		java.sql.Statement s = DBUtility.getStatement();

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

				dm.addRow(new String[] { nome, costo, quantita });

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return dm;
	}

	@Override


	/**
	 * Eliminazione Prodotto dalla lista spesa e aggiornamento dell'importo finale. 
	 * @param  c Entity Carrello
	 * @param  shop  Entity Spesa
	 * @return boolean
	 */
	public boolean deleteProduct(Carrello c, Spesa shop)
			throws SQLException {

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// ' " ' "
		String deleteTableSQL = "delete from carrello where prodotto_barcode='"
				+ c.getBarcode() + "' and spesa_idspesa= '" + shop.getIdspesa() + "'";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(deleteTableSQL);

			// execute insert SQL stetement
			preparedStatement.execute(deleteTableSQL);

			return true;

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return false;

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}



	@Override
	/**
	 * Calcolo dell'importo finale della spesa.
	 * Il calcolo viene eseguito direttamente nella query.
	 * @param shop Entity Spesa
	 * 
	 */
	public void calcoloImporto(Spesa shop) throws SQLException {
		// TODO Auto-generated method stub

		double importo_finale = 0;

		java.sql.Statement s = DBUtility.getStatement();

		String sql = "select sum(p.costo*c.quantita) importo from prodotto p,carrello c,spesa s where s.idspesa= '"
				+ shop.getIdspesa()
				+ "' and s.idspesa=c.spesa_idspesa and c.prodotto_barcode=p.barcode";

		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				importo_finale = rs.getDouble("importo");

			}
			// set di importo totale con il result della query
			shop.setImporto_tot(importo_finale);

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		finally {

			if (s != null) {
				s.close();
			}

		}

	}

	/**
	 * Consente di aggiornare la quantità del prodotto da acquistare.
	 * @param carrello Entity Carrello
	 * @param shop   Entity Spesa
	 * @return boolean
     **/
	@Override
	public boolean updateProduct(Carrello carrello, Spesa shop)
			throws SQLException {

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// ' " " '
		String updateTableSQL = "update carrello set quantita ='" + carrello.getQuantita()
				+ "' where prodotto_barcode='" + carrello.getBarcode()
				+ "' and spesa_idSpesa='" + shop.getIdspesa() + "'";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			// execute insert SQL stetement
			preparedStatement.execute(updateTableSQL);

			return true;

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return false;

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	/**
	 * Consente di eliminare la spesa dal database
	 * @param shop Entity Spesa
	 * @return boolean
	 */
	@Override
	public boolean cancellaSpesa(Spesa shop) throws SQLException {

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// '"+idspesa+"'
		String deleteSpesaSQL = "DELETE FROM carrello WHERE spesa_idSpesa  = '"
				+ shop.getIdspesa() + "'";
		String deleteSQL2 = "DELETE FROM spesa WHERE idSpesa = '" + shop.getIdspesa()
				+ "'";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(deleteSpesaSQL);

			// execute insert SQL stetement
			preparedStatement.execute(deleteSpesaSQL);
			preparedStatement.execute(deleteSQL2);

			return true;

		} catch (SQLException e) {

			e.printStackTrace();
			throw e;

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
	}



	@Override
	public int getIdScheda(int idspesa) throws SQLException{

		int idscheda=0;
		java.sql.Statement s = DBUtility.getStatement();

		String sql = "select scheda_idscheda from spesa where idspesa='"+idspesa+"' ";
		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				idscheda=rs.getInt("scheda_idscheda");


			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return idscheda;
	}

/**
 * Mostra il massimale residuo dell'utente
 *   
 */
	@Override
	public boolean show_Massimale(Scheda card)
			throws SQLException {


		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// ' " " '
		String updateTableSQL = "update scheda set massimale_res ='" + card.getMassimale_res()
				+ "' where idscheda='"+card.getIdScheda()+"' ";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			// execute insert SQL stetement
			preparedStatement.execute(updateTableSQL);

			return true;

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return false;

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	/**
	 * Calcolo punti tessera fedeltà per la singola spesa  
	 * Il calcolo punti viene eseguito dentro la query : SUM (quantita*punti prodotto) as punti.
	 * Il result della query, viene assegnato alla spesa con il metodo setPunti_spesa
	 * @param shop Entity Spesa
	 */
	@Override
	public void CalcolaPuntiSpesa(Spesa shop) throws SQLException {
		// TODO Auto-generated method stub



		int punti_spesa=0;

		java.sql.Statement s = DBUtility.getStatement();

		String sql = "select sum(c.quantita * p.punti_prod) AS punti from carrello c, prodotto p, spesa s "
				+ "where s.idspesa='"+shop.getIdspesa()+"' and s.idspesa=c.spesa_idspesa and c.prodotto_barcode=p.barcode";
		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				punti_spesa = rs.getInt("punti");
				shop.setPunti_spesa(punti_spesa);

			}

			// assegno a shop, il valore dei punti
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		finally {

			if (s != null) {
				s.close();
			}

		}

	}

	/**
	 * Aggiorna i punti della scheda sommando i punti precendenti con quelli dell'ultima spesa
	 * @param card Entity Scheda
	 * @param shop Entity Spesa
	 */
	@Override
	public void AggiornaPuntiScheda(Scheda card,Spesa shop) throws SQLException {
		java.sql.Statement s = DBUtility.getStatement();
		double punti_aggiornati=0;
		double punti_totali_scheda=0;

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;
		String sql = "select punti_totali from scheda where idscheda='"+card.getIdScheda()+"' ";
		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				punti_totali_scheda=rs.getDouble("punti_totali");

				card.setPunti_totali(punti_totali_scheda);

			}

			punti_aggiornati=shop.getPunti_spesa()+card.getPunti_totali();





			String updateTableSQL = "update scheda set punti_totali ='" + punti_aggiornati
					+ "' where idscheda='"+card.getIdScheda()+"' ";

			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			// execute insert SQL stetement
			preparedStatement.execute(updateTableSQL);


			//aggiorno i punti
			card.setPunti_totali(punti_aggiornati);


		}


		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}

