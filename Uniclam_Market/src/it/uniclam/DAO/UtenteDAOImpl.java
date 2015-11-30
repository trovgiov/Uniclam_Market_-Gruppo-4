package it.uniclam.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

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
	public void updateUtente(Utente u,String mail) throws SQLException {
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;


		Statement s = DBUtility.getStatement();
 		String updateQuery = "UPDATE utente SET email ='"
				+mail + "'   WHERE email = '"
				+ u.getEmail() + "' ";
		int n = s.executeUpdate(updateQuery);

	}

	@Override
	public void deleteUtente(String email) {
		// TODO Auto-generated method stub

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;
		JOptionPane.showMessageDialog(null,"Dentro la f"+ email.length()); 

		String deleteSQL ="DELETE utente WHERE email= '"+email+"'";

		try {
		 
			preparedStatement = DBUtility.getPreparedStatement(deleteSQL);

			//.setString(1, email);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}


	}




	@Override
	public boolean login(int id, int pin) throws SQLException {
		// TODO Auto-generated method stub
		 

		boolean login_succed = false;
 
		java.sql.Statement s = DBUtility.getStatement();
 		String sql = " select scheda_idScheda,pin from login where scheda_idScheda='" +id+ "' and pin= '"+pin+"' ";

		try {
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {

			login_succed=true ;

			}
			 
 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return login_succed;
	}

	
	 


}
