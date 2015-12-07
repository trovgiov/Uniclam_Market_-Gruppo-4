package it.uniclam.DAO;

 
import it.uniclam.entity.Spesa;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public interface SpesaDAO {

	int insertSpesa(Spesa c) throws SQLException;

	public boolean addProducts(String barcode, int idscheda, int quantita)
			throws SQLException;

	public DefaultTableModel getData(int idspesa) throws SQLException;

	public boolean deleteProduct(String barcode, int idspesa)
			throws SQLException;

	public double calcoloImporto(int idspesa) throws SQLException;

	boolean updateProduct(String barcode, int quantita, int idspesa)
			throws SQLException;

	public boolean cancellaSpesa(int idSpesa) throws SQLException;
	
	
 
 
	boolean updateMassimale(double mas_res, int idscheda) throws SQLException;

	int getIdScheda(int idspesa) throws SQLException;

	public   int CalcolaPuntiSpesa(int idspesa) throws SQLException;

	public int AggiornaPuntiSpesa(int idscheda,int ruggero) throws SQLException;
}
