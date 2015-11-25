package it.uniclam.DAO;

import it.uniclam.db.DBUtility;
 import it.uniclam.entity.Scheda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

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
	public void activeCard(Scheda s,String email_Utente) throws SQLException {
 
		

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		Date today=new Date ();
		
		String insertTableSQL = "insert into scheda (punti_totali,massimale_res,utente_email) VALUES (?,?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

 
			preparedStatement.setDouble(1,s.getPunti_totali());

			preparedStatement.setDouble(2,s.getMassimale_res());
			

			preparedStatement.setString(3,email_Utente);
			
			
 			 

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
	public int[] generatePin(String m) throws SQLException {
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		//Seleziono l'id scheda corrispondente alla mail in ingresso
		 
java.sql.Statement s = DBUtility.getStatement();
int idScheda=0;
		String sql = " Select idScheda from scheda where utente_email='"+m+"'  ";
		
		try {
			ResultSet rs= s.executeQuery(sql);
			
 			
			
			while(rs.next()){
				  idScheda=rs.getInt("idScheda");
				 
			}
			
	
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
		
		//Crea Pin
 		Random random = new Random();
		int j = 3000;
		int n = 7000 - j;
		int pin = random.nextInt(n) + j;
		
		String insertTableSQL = "insert into login (pin,scheda_idScheda) VALUES (?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

 
			preparedStatement.setInt(1,pin);

			preparedStatement.setInt(2,idScheda);
			

		 
			 

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
 int [] a = {idScheda,pin};
	 
		
		return a;
		
		
		
		
		
		
		
		
		
		
		
		
	 
	}

}
