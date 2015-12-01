package it.uniclam.DAO;

import it.uniclam.db.DBUtility;
import it.uniclam.entity.Spesa;

import java.sql.SQLException;
import java.util.Date;

import com.mysql.jdbc.Connection;

public class SpesaDAOImpl implements SpesaDAO{

	private SpesaDAOImpl() {
	}

	private static SpesaDAO dao = null;

	public static SpesaDAO getInstance() {
		if (dao == null) {
			dao = new SpesaDAOImpl();
		}
		return dao;
	}
	
	
	@Override
	public void insertSpesa(Spesa c) throws SQLException {
 
		
		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// Date today = new Date();

		String insertTableSQL = "insert into spesa (scheda_idScheda,data_spesa) VALUES (?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
             
            
  			preparedStatement.setInt(1, c.getIdscheda() );
  			preparedStatement.setDate(2, (java.sql.Date) c.getData_spesa());

 
 
			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Inserimento Data Spesa Effettuato");

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
