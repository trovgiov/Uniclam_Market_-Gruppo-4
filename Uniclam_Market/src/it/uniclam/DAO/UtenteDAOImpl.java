package it.uniclam.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

import it.uniclam.db.DBUtility;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;

public class UtenteDAOImpl implements UtenteDAO {

	private UtenteDAOImpl() {
	}

	private static UtenteDAO dao = null;

	public static UtenteDAO getInstance() {
		if (dao == null) {
			dao = new UtenteDAOImpl();
		}
		return dao;
	}
	
	
	
	/**
	 * Metodo per inserire l'utente nel DB
	 */

	@Override
	public void insertUtente(Utente u) throws SQLException {
 
		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		String insertTableSQL = "insert into utente (nome,cognome,email,telefono,massimale_tot) VALUES (?,?,?,?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setString(1, u.getNome());
			preparedStatement.setString(2, u.getCognome());
			preparedStatement.setString(3, u.getEmail());
			preparedStatement.setString(4, u.getTelefono());
			preparedStatement.setDouble(5, u.getMassimale());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Inserimento Effettuato");

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			throw e ;

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	@SuppressWarnings("unused")
	@Override
	public void updateUtente(Utente u, String new_mail) throws SQLException {
		// TODO Auto-generated method stub

		Statement s = DBUtility.getStatement();
		String updateQuery = "UPDATE utente SET email = '" + new_mail
				+ "'   WHERE email = '" + u.getEmail() + "' ";
		try {

			int n = s.executeUpdate(updateQuery);
		}

		catch (SQLException e) {
 			e.printStackTrace();
 			throw e;
		}
		s.close();
	}

	@Override
	public void deleteUtente(Utente u) throws SQLException {
		// TODO Auto-generated method stub

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE from utente WHERE email='" + u.getEmail() + "'";

		try {

			preparedStatement = DBUtility.getPreparedStatement(deleteSQL);

			// preparedStatement.setString(1, email);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	/**
	 * Metodo che consente l'autenticazione. Restituisce true se le credenziali inserite si trovano nel database, altrimenti non consente l'accesso al sistema
	 */
	@Override
	public boolean login(Scheda scheda) throws SQLException {
 
		boolean login_succed = false;

		java.sql.Statement s = DBUtility.getStatement();
		String sql = " select scheda_idScheda,pin from login where scheda_idScheda='"
				+ scheda.getIdScheda() + "' and pin= '" + scheda.getPin() + "' ";

		try {
			ResultSet rs = s.executeQuery(sql);

			// se la query ottiene un result ,allora il login è stato effettuato con successo. 
			//Se la query è vuota, la variabile boolean non cambia il suo stato
			
			if (rs.next()) {

				login_succed = true;

			}

		} catch (SQLException e) {
 			e.printStackTrace();
			throw e;
		}

		return login_succed;
	}

}
