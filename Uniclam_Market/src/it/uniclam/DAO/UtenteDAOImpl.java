package it.uniclam.DAO;

 import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import it.uniclam.db.DBUtility;
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

	@Override
	public void insertUtente(Utente u) throws SQLException {
		// TODO Auto-generated method stub

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
	public void updateUtente(Utente u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUtente(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean login(int id, int pin) throws SQLException {
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		boolean login_succed=true;
		// Seleziono l'id scheda corrispondente alla mail in ingresso

		java.sql.Statement s = DBUtility.getStatement();
		int idScheda = 0;
		String sql = " select scheda_idScheda,pin from login where scheda_idScheda='" +id+ "' and pin= '"+pin+"' ";

		try {
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {

				 login_succed=true;
  				
			}
			else 
				return  login_succed=false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
		
		
		return login_succed;
	}

 	 

}
