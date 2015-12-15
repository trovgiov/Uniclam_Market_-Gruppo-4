package it.uniclam.DAO;

 import it.uniclam.db.DBUtility;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;
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


/**
 * Implementazione metodi interfaccia SchedaDAO
 * @author Giovanni Trovini
 *
 */
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

	
	/**
	 * Si occupa di attivare la tessera fedelta' e inserire i dati nel database.
	 * 
	 * @param s
	 * @param u
	 * @throws SQLException
	 */
	 
	@Override
	public void activeCard(Scheda s, Utente u) throws SQLException {

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// Date today = new Date();

		String insertTableSQL = "insert into scheda (punti_totali,massimale_res,utente_email) VALUES (?,?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setDouble(1, s.getPunti_totali());

			preparedStatement.setDouble(2, s.getMassimale_res());

			preparedStatement.setString(3, u.getEmail());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Scheda attivata");

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

	}

	/**
	 * Metodo per generare id scheda e pin. Il pin a 4 cifre viene generato casualmente con la funzione random.
	 * @param u Utente
	 * @return pin Restituisce il pin univoco appartenente all'utente che ha effettuato la registrazione
	 * 
	 *  @throws SQLException
	 */

	@Override
	public int[] generatePin(Utente u) throws SQLException {

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// Seleziono l'id scheda corrispondente alla mail dell'utente in ingresso

		java.sql.Statement s = DBUtility.getStatement();
		int idScheda = 0;
		String sql = " Select idScheda from scheda where utente_email='" + u.getEmail()
				+ "'  ";

		try {
			ResultSet rs = s.executeQuery(sql);

			//Assegno alla variabile id scheda, il relativo valore prelevato dal database	

			while (rs.next()) {
				idScheda = rs.getInt("idScheda");

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

			throw e;
		}


		// Crea Pin con la funzione random
		Random random = new Random();
		int j = 3000;
		int n = 7000 - j;
		int pin = random.nextInt(n) + j;

		String insertTableSQL = "insert into login (pin,scheda_idScheda) VALUES (?,?)";


		Connection dbConnection2 = null;
		java.sql.PreparedStatement preparedStatement2 = null;


		try {
			dbConnection2 = DBUtility.getDBConnection();

			preparedStatement2 = dbConnection2.prepareStatement(insertTableSQL);

			preparedStatement2.setInt(1, pin);

			preparedStatement2.setInt(2, idScheda);

			// execute insert SQL stetement
			preparedStatement2.executeUpdate();

			System.out.println("Inserimento Effettuato");

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

			if (dbConnection2 != null) {
				dbConnection2.close();
			}

			if (preparedStatement2 != null) {
				preparedStatement2.close();
			}


			if (s != null) {
				s.close();
			}



		}

		// creo l'array finale
		int[] a = { idScheda, pin };

		return a;

	}
 

	/**
	 * Metodo che fornisce una funzione aggiuntiva al progetto.
	 * Consente di recuperare i dati di accesso al sistema, tramite l'invio di una mail all'indirizzo specificato in fase di registrazione.
	 * @param u Utente

	 * @throws SQLException
	 */
	
	@Override
	public void recovery_pin(Utente u) throws SQLException,

	AddressException, MessagingException {

		int pin = 0;
		int idscheda = 0;

		// Controllo se l'email è presente nel db

		java.sql.Statement s = DBUtility.getStatement();
		String sql = " Select email from utente where email='" + u.getEmail() + "'  ";

		try {
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {

				// Cerco il Pin con l'email inserita

				java.sql.Statement s1 = DBUtility.getStatement();
				String sql2 = "select pin,scheda_idScheda from utente u ,scheda s ,login l where u.email='"
						+ u.getEmail()
						+ "' and u.email=s.utente_email and s.idscheda=l.scheda_idscheda";

				ResultSet rs2 = s1.executeQuery(sql2);

				while (rs2.next()) {
					pin = rs2.getInt("pin");
					idscheda = rs2.getInt("scheda_idScheda");

				}
				rs2.close();


				SendEmail.Email_Recovery(idscheda, pin, u.getEmail());

			} else {
				JOptionPane.showMessageDialog(null,
						"La sua mail non è presente nei nostri sistemi.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e ;
		}

		finally {

			if (s != null) {
				s.close();
			}

		}

	}

	/**
	 * Metodo che si occupa di controllare e resituire il massimale. Il controllo avviene secondo le caratteristiche progettuali
	 * Se il mese, in cui l'utente accede al sistema, corrisponde al mese dell'ultima spesa effetuato, il sistema restituisce il massimale reiduo.
	 * Se il mese, in cui l'utente accede al sistema, corrisponde a un mese diverso dall'ultima spesa, il sistema resetta il massimale residuo a quello totale.
	 * @param card Scheda
	 * @return Restituisce il massimale 
	 * @throws SQLException
	 */

	@SuppressWarnings("unused")
	@Override
	public double checkMassimale(Scheda card) throws SQLException {

		double massimale_residuo = 0;
		double massimale_totale=0;
		int month_spesa=0;
		int month_today=0;

		//  DATA SPESA SQL

		Date data_spesaSQL=null;         

		//DATA DI OGGI	IN JAVA UTIL
		java.util.Date today = new java.util.Date();  

		java.sql.Statement s = DBUtility.getStatement();
		java.sql.Statement s1 = DBUtility.getStatement();
		java.sql.Statement s2 = DBUtility.getStatement();

		// query che seleziona il massimale residuo e quello totale, accedendo alle rispettive tabelle Scheda & Utente
		String sql = " select s.massimale_res, u.massimale_tot from scheda s, utente u where s.idScheda = '"+card.getIdScheda()+"' and s.utente_email = u.email";
		try {

			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				massimale_residuo = rs.getDouble("s.massimale_res");
				massimale_totale=rs.getDouble("u.massimale_tot");

			}

			System.out.println("Massimale tot "+massimale_totale+" Massimale residuo : "+massimale_residuo);


			// Query che seleziona tutte le date delle spese effettuate,relative all'id scheda corrispondente.
			// Le date vengono inserite in un arrayList

			String sql1 = " select data_spesa from spesa where scheda_idscheda= '"+card.getIdScheda()+"' ORDER BY data_spesa DESC ";

			//Inizializzo l'arrayList<Date>
			ArrayList<Date> dataspesa = new ArrayList<Date>();


			ResultSet rs1 = s1.executeQuery(sql1);


			while (rs1.next()) {

				data_spesaSQL = rs1.getDate("data_spesa");

				dataspesa.add(data_spesaSQL);

			}

			//Ho l'arrayList riempito

			//CASO IN CUI L'UTENTE NON HA MAI FATTO NESSUNA SPESA. ARRAYLIST<DATE>=0

			if(dataspesa.size()==0){

				System.out.println("\n Nessuna data nel db.INIZIALIZZO MASSIMALE RESIDUO A QUELLO TOTALE");


				massimale_residuo=massimale_totale;

				Statement s3 = DBUtility.getStatement();

				String updateTableSQL = "update scheda set massimale_res = '"+massimale_residuo+"' where idscheda=45";

				int n = s3.executeUpdate(updateTableSQL);



				return massimale_residuo;

			}

			//CASO IN CUI L'UTENTE HA GIA' FATTO PRECEDENTI SPESE


			// prendo il primo record delL'ArrayList : equivale alla data dell'ultima spesa

			Date data_ultimaSpesaSQL = dataspesa.get(0);
			System.out.println("data ultima spesa : "+data_ultimaSpesaSQL);

			// Cast DataSQL a Date (java.util.date)

			java.util.Date data_Spesa = new java.util.Date(data_ultimaSpesaSQL.getTime());

			// prendo il mese della data spesa
			month_spesa = data_Spesa.getMonth();  
			// prendo il mese della data odierna
			month_today= today.getMonth();       


			if(month_spesa>month_today || month_spesa<month_today){

				massimale_residuo=massimale_totale;

				String updateTableSQL = "update scheda set massimale_res = '"+massimale_residuo+"' where idscheda='"+card.getIdScheda()+"'";

				int n = s2.executeUpdate(updateTableSQL);

			}

			rs.close();

			rs1.close();	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
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



	/**
	 * Metodo che restituisce l'utente (informazioni personali) che ha effettuato il login 
	 * @param card Scheda
	 * @return Utente
	 *  @throws SQLException
	 */

	@Override
	public Utente checkUser(Scheda card) throws SQLException {
		Utente u = null;
		java.sql.Statement s = DBUtility.getStatement();
		String sql = "	select u.nome, u.cognome,u.email from utente u,scheda s where idscheda= '"
				+ card.getIdScheda() + "' and s.utente_email=u.email";
		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				u = new Utente(rs.getString("u.nome"),
						rs.getString("u.cognome"), rs.getString("u.email"));

			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return u;

	}


	@Override
	/**
	 * Metodo che seleziona , tramite query, i punti totali a partire dall'id scheda.
	 * Restituisce i punti totali
	 * @param card Scheda
	 * @retrun Punti Acquisiti
	 *  @throws SQLException
	 */
	public double show_points(Scheda card) throws SQLException {

		int punti=0;

		java.sql.Statement s = DBUtility.getStatement();
		String sql = "select punti_totali from scheda where idscheda= '"+card.getIdScheda()+"' ";
		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				punti=rs.getInt("punti_totali");

			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}





		return punti;
	}




	@Override

	/**
	 * Eseguo l'update del massimale residuo e lo inserisco nel db
	 * @param card
	 *  @throws SQLException
	 */
	public void UpdateMassimaleResiduo(Scheda card) throws SQLException {
		// TODO Auto-generated method stub


		Statement s = DBUtility.getStatement();
		String updateQuery =  "update scheda set massimale_res='"+card.getMassimale_res()+"' where idscheda='"+card.getIdScheda()+"' ";
		try {

			int n = s.executeUpdate(updateQuery);
		}

		catch (SQLException e) {
			e.printStackTrace();
			throw e;

		}

	}



}
