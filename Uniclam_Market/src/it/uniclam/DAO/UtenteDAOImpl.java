package it.uniclam.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

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
	public void generatePin(String email) throws SQLException {
		// TODO Auto-generated method stub

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		Random random = new Random();
		int j = 3000;
		int n = 7000 - j;
		int pin = random.nextInt(n) + j;
		java.sql.Statement s = DBUtility.getStatement();
		
		int idUtente = 0;
		String sql = " Select idUtente from utente where email = '" + email
				+ "' ";

		try {

			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				idUtente = rs.getInt("idUtente");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.close();

		// insert into scheda (Utente_idUtente) values (13)

		String insertTableSQL = "insert into login (pin,Utente_idUtente) VALUES (?,?)";

		try {

			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, pin);
			preparedStatement.setInt(2, idUtente);

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Inserimento Pin Effettuato");

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
