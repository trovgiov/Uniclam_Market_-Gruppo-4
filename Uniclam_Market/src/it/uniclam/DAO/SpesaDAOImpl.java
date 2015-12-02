package it.uniclam.DAO;

import it.uniclam.db.DBUtility;
import it.uniclam.entity.Spesa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import javax.swing.table.DefaultTableModel;

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
	public int insertSpesa(Spesa c) throws SQLException {


		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;

		// Date today = new Date();
		Random random = new Random();
		int j = 30;
		int n = 70 - j;
		int idspesa = random.nextInt(n) + j;

		String insertTableSQL = "insert into spesa (idspesa,scheda_idScheda,data_spesa) VALUES (?,?,?)";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, idspesa );

			preparedStatement.setInt(2, c.getIdscheda() );
			preparedStatement.setDate(3, (java.sql.Date) c.getData_spesa());



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
		return idspesa;

	}


	@Override
	public boolean addProducts(String barcode, int idspesa, int quantita) throws SQLException {

		Connection dbConnection = null;
		java.sql.PreparedStatement preparedStatement = null;


		String insertTableSQL = "insert into carrello(prodotto_barcode,spesa_idSpesa,quantita)  VALUES ('"+barcode+"','"+idspesa+"', '"+quantita+"')";

		try {
			dbConnection = DBUtility.getDBConnection();

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);



			// execute insert SQL stetement
			preparedStatement.execute(insertTableSQL);


			return true ;






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
		return false;

	}


	@Override
	public DefaultTableModel getData(int idspesa) throws SQLException {

		//Aggiungo le colonne alla tabella

		DefaultTableModel dm = new DefaultTableModel();
		dm.addColumn("Barcode");
		dm.addColumn("Quantita");
		dm.addColumn("Prezzo");


		//sql
		java.sql.Statement s = DBUtility.getStatement();

		String sql = "select p.nome,p.costo,c.quantita from prodotto p, carrello c, spesa s where s.idspesa= '"+idspesa+"' and s.idspesa=c.spesa_idspesa and c.prodotto_barcode=p.barcode";


		try{
			ResultSet rs=s.executeQuery(sql);



			while(rs.next()){

				String nome=rs.getString("p.nome");
				Double costo_p=rs.getDouble("p.costo");
				int quantita_p=rs.getInt("c.quantita");

				String costo=String.valueOf(costo_p);
				String quantita=String.valueOf(quantita_p);


				dm.addRow(new String []{nome,costo,quantita});

			}




		}

		catch(Exception e){
			e.printStackTrace();
		}





		return dm;
	}







}
