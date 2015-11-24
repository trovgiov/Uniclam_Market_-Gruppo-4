package it.uniclam.DAO;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import it.uniclam.db.DBUtility;
import it.uniclam.entity.Utente;


public class UtenteDAOImpl implements UtenteDAO {

	
private UtenteDAOImpl(){}
	
	private static UtenteDAO dao = null;
	
	public static UtenteDAO getInstance(){
		if (dao == null){
			dao = new UtenteDAOImpl();
		}
		return dao;
	}
	
	
	
	
	@Override
	public void insertUtente(Utente u) throws SQLException {
		// TODO Auto-generated method stub
		
		
		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		String insertTableSQL = "insert into utente (idutente,nome,cognome,email,telefono,massimale_tot) VALUES (?,?,?,?,?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();
			
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, u.getIdUtente() );

			preparedStatement.setString(2, u.getNome() );
			preparedStatement.setString(3, u.getCognome());
			preparedStatement.setString(4, u.getEmail());
			preparedStatement.setString(5, u.getTelefono());
			preparedStatement.setDouble(6, u.getMassimale());


			
			
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

}
