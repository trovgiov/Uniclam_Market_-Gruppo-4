package it.uniclam.UniclamMarket;

 import it.uniclam.db.DBUtility;
 
 

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 

 

 
public class Test {
 

	
 	 





	public static void main(String[] args) throws SQLException{

		
 		
	 
		
 	Double pino=pino();
		System.out.println("\nMassimale _ residuo : "+pino);
 	}


	@SuppressWarnings({ "deprecation", "unused" })
	public static double pino() throws SQLException{

		double massimale_residuo = 0;
		double massimale_totale=0;
		int month_spesa=0;
		int month_today=0;

		Date data_spesaSQL=null; // DATA SPESA SQL
		java.util.Date today = new java.util.Date(); //DATA DI OGGI	IN JAVA UTIL

		java.sql.Statement s = DBUtility.getStatement();
		java.sql.Statement s1 = DBUtility.getStatement();


		String sql = " select s.massimale_res, u.massimale_tot from scheda s, utente u where s.idScheda = 45 and s.utente_email = u.email";
		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				massimale_residuo = rs.getDouble("s.massimale_res");
				massimale_totale=rs.getDouble("u.massimale_tot");

			}

			System.out.println("\nMassimale tot "+massimale_totale+" Massimale residuo : "+massimale_residuo);


			String sql1 = " select data_spesa from spesa where scheda_idscheda= 45 ORDER BY data_spesa DESC ";

			ArrayList<Date> dataspesa = new ArrayList<Date>();


			ResultSet rs1 = s1.executeQuery(sql1);

			while (rs1.next()) {

				data_spesaSQL = rs1.getDate("data_spesa");

				dataspesa.add(data_spesaSQL);

			}
			System.out.println("\nSize arrayList data : " +dataspesa.size());


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
			System.out.println("\ndata ultima spesa : "+data_ultimaSpesaSQL);





			// ho la data spesa nel formato java util

			java.util.Date data_Spesa = new java.util.Date(data_ultimaSpesaSQL.getTime());

			month_spesa = data_Spesa.getMonth(); // prendo il mese della data spesa
			month_today= today.getMonth();      // prendo il mese della data odierna




			if(month_spesa>month_today || month_spesa<month_today){

				massimale_residuo=massimale_totale;

				Statement s2 = DBUtility.getStatement();

				// ' " ' "
				String updateTableSQL = "update scheda set massimale_res = '"+massimale_residuo+"' where idscheda=45";

				int n = s2.executeUpdate(updateTableSQL);



				s2.close();
			}
			rs.close();

			rs1.close();	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				if (s != null) {
					s.close();
				}
			}
		//JOptionPane.showMessageDialog(null, "Mese totale "+month_spesa+" Mese odierno: "+month_today);

		System.out.println("Mese spesa : "+month_spesa + ""+" Mese oggi : "+month_today);

		return massimale_residuo;



	}

		

 
}




