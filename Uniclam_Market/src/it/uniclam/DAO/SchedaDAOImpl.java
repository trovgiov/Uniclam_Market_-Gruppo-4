package it.uniclam.DAO;

import it.uniclam.db.DBUtility;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;
import it.uniclam.mail.EmailUtility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

public class SchedaDAOImpl implements SchedaDAO {

	private SchedaDAOImpl() {
	}

	private static SchedaDAO dao = null;

	public static SchedaDAO getInstance() {
		if (dao == null) {
			dao = new SchedaDAOImpl();
		}
		return dao;
	}

	@Override
	public void activeCard(Scheda s, String email_Utente) throws SQLException {

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// Date today = new Date();

		String insertTableSQL = "insert into scheda (punti_totali,massimale_res,utente_email) VALUES (?,?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setDouble(1, s.getPunti_totali());

			preparedStatement.setDouble(2, s.getMassimale_res());

			preparedStatement.setString(3, email_Utente);

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

	@SuppressWarnings("resource")
	@Override
	public int[] generatePin(String mail) throws SQLException {
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// Seleziono l'id scheda corrispondente alla mail in ingresso

		java.sql.Statement s = DBUtility.getStatement();
		int idScheda = 0;
		String sql = " Select idScheda from scheda where utente_email='" + mail
				+ "'  ";

		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				idScheda = rs.getInt("idScheda");

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			if (s != null) {
				s.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		// Crea Pin
		Random random = new Random();
		int j = 3000;
		int n = 7000 - j;
		int pin = random.nextInt(n) + j;

		String insertTableSQL = "insert into login (pin,scheda_idScheda) VALUES (?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, pin);

			preparedStatement.setInt(2, idScheda);

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Inserimento Effettuato");
			// Utente a = null;
			// System.out.println(a.getEmail());

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
		int[] a = { idScheda, pin };

		return a;

	}

	// Funziona Extra per il recupero pin

	@Override
	public void recovery_pin(String email) throws SQLException,
			AddressException, MessagingException {

		int pin = 0;
		int idscheda = 0;

		// Controllo se l'email è presente nel db

		java.sql.Statement s = DBUtility.getStatement();
		String sql = " Select email from utente where email='" + email + "'  ";

		try {
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {

				// Cerco il Pin con l'email inserita

				java.sql.Statement s1 = DBUtility.getStatement();
				String sql2 = "select pin,scheda_idScheda from utente u ,scheda s ,login l where u.email='"
						+ email
						+ "' and u.email=s.utente_email and s.idscheda=l.scheda_idscheda";

				ResultSet rs2 = s1.executeQuery(sql2);

				while (rs2.next()) {
					pin = rs2.getInt("pin");
					idscheda = rs2.getInt("scheda_idScheda");

				}
				rs2.close();

				// Creo il messaggio
				String oggetto = "Recupero Numero Scheda e Pin - Uniclam Market";
				String message_recovery = "Salve, come da lei richiesto, ecco i dati necessari per il recupero dei dati di accesso al sistema Uniclam market"
						+ "\n\n"
						+ "Numero Carta "
						+ idscheda
						+ "\n"
						+ "Pin : "
						+ pin
						+ "\n\n"
						+ "Se non ha richiesto il recupero del pin, ignori questa mail."
						+ "\n\n" + "Saluti - Uniclam Market ";

				EmailUtility.sendEmail(EmailUtility.HOST, EmailUtility.PORT,
						EmailUtility.USER, EmailUtility.PASSWORD, email,
						oggetto, message_recovery);

			} else {
				JOptionPane.showMessageDialog(null,
						"La sua mail non è presente nei nostri sistemi.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			if (s != null) {
				s.close();
			}

		}

	}

	@Override
	public double checkMassimale(int idscheda) throws SQLException {
		double massimale_residuo = 0;
		// Utente u = null;

		java.sql.Statement s = DBUtility.getStatement();

		String sql = " select s.massimale_res, u.nome, u.cognome from scheda s, utente u where s.idScheda = '"
				+ idscheda + "' and s.utente_email = u.email";
		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				massimale_residuo = rs.getDouble("massimale_res");
				// u = new Utente(rs.getString("u.nome"),
				// rs.getString("u.cognome"));

			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (s != null) {
				s.close();
			}
		}

		return massimale_residuo;
	}

	@Override
	public Utente checkUser(int idScheda) throws SQLException {
		// TODO Auto-generated method stub
		Utente u = null;
		java.sql.Statement s = DBUtility.getStatement();
		String sql = "	select u.nome, u.cognome,u.email from utente u,scheda s where idscheda= '"
				+ idScheda + "' and s.utente_email=u.email";
		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				u = new Utente(rs.getString("u.nome"),
						rs.getString("u.cognome"), rs.getString("u.email"));

			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return u;

	}

}
