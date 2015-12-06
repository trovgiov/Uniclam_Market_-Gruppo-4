package it.uniclam.DAO;

import it.uniclam.GUI.Login_GUI;
import it.uniclam.db.DBUtility;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;
import it.uniclam.mail.EmailUtility;
import it.uniclam.mail.SendEmail;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

 SendEmail.Email_Recovery(idscheda, pin, email);
				
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
		double massimale_totale=0;
		int month_spesa=0;
		int month_today=0;

		Date data_spesaSQL=null;                     //  DATA SPESA SQL
		java.util.Date today = new java.util.Date(); //DATA DI OGGI	IN JAVA UTIL

		java.sql.Statement s = DBUtility.getStatement();
		java.sql.Statement s1 = DBUtility.getStatement();
		java.sql.Statement s2 = DBUtility.getStatement();


		String sql = " select s.massimale_res, u.massimale_tot from scheda s, utente u where s.idScheda = '"+idscheda+"' and s.utente_email = u.email";
		try {

			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				massimale_residuo = rs.getDouble("s.massimale_res");
				massimale_totale=rs.getDouble("u.massimale_tot");

			}

			System.out.println("Massimale tot "+massimale_totale+" Massimale residuo : "+massimale_residuo);


			String sql1 = " select data_spesa from spesa where scheda_idscheda= '"+idscheda+"' ORDER BY data_spesa DESC ";

			ArrayList<Date> dataspesa = new ArrayList<Date>();


			ResultSet rs1 = s1.executeQuery(sql1);

			while (rs1.next()) {

				data_spesaSQL = rs1.getDate("data_spesa");

				dataspesa.add(data_spesaSQL);

			}


			//caso in cui l'utente non ha mai fatto la spesa. size arrayList delle date è 0
			if(dataspesa.size()==0){

				System.out.println("\n Nessuna data nel db.INIZIALIZZO MASSIMALE RESIDUO A QUELLO TOTALE");


				massimale_residuo=massimale_totale;

				Statement s3 = DBUtility.getStatement();

				String updateTableSQL = "update scheda set massimale_res = '"+massimale_residuo+"' where idscheda=45";

				int n = s3.executeUpdate(updateTableSQL);



				return massimale_residuo;

			}


			// prendo il primo record della tabella : equivale alla data dell'ultima spesa
			Date data_ultimaSpesaSQL = dataspesa.get(0);
			System.out.println("data ultima spesa : "+data_ultimaSpesaSQL);

			// ho la data spesa nel formato java util

			java.util.Date data_Spesa = new java.util.Date(data_ultimaSpesaSQL.getTime());

			month_spesa = data_Spesa.getMonth(); // prendo il mese della data spesa
			month_today= today.getMonth();      // prendo il mese della data odierna

			if(month_spesa>month_today || month_spesa<month_today){

				massimale_residuo=massimale_totale;

				// ' " ' "
				String updateTableSQL = "update scheda set massimale_res = '"+massimale_residuo+"' where idscheda='"+idscheda+"'";

				int n = s2.executeUpdate(updateTableSQL);

			}

			rs.close();

			rs1.close();	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (s != null) {
				s.close();
			}
			if (s1 != null) {
				s.close();
			}
			if (s2 != null) {
				s.close();
			}
		}

		System.out.println("mese : "+month_spesa + ""+" Mese oggi : "+month_today);

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
