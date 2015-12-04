package it.uniclam.UniclamMarket;

import it.uniclam.db.DBUtility;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Test {

	@SuppressWarnings({ "deprecation", "unused" })
	public static void main(String[] args) throws SQLException {
		java.util.Date x = new java.util.Date();
		Date date = null;
		java.sql.Statement s = DBUtility.getStatement();

		String sql = " select data_spesa from spesa where scheda_idscheda=45 ORDER BY data_spesa DESC ";
		ArrayList<Date> dataspesa = new ArrayList<Date>();

		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {

				date = rs.getDate(1);
				dataspesa.add(date);

			}

			Date data_ultimaSpesaSQL = dataspesa.get(1);

			// ho la data spesa nel formato java util
			java.util.Date data_Spesa = new java.util.Date(
					data_ultimaSpesaSQL.getTime());

			int month = data_Spesa.getMonth(); // prendo il mese

			System.out.println("Mese della data inserita nel db : " + month);

			System.out.println("Mese della data odierna : " + x.getMonth());

			// System.out.println(cal.toString());

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		finally {

			if (s != null) {
				s.close();
			}

		}

	}

}
